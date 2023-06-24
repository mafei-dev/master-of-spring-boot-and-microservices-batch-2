package com.example.orderservice.service.internal;

import com.example.orderservice.dto.CreateOrderDTO;
import com.example.orderservice.dto.OrderStatusDTO;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.entity.OrderStatusEntity;
import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.exception.UserNotActiveException;
import com.example.orderservice.model.external.NewOrderPaymentModel;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.model.external.ViewOrderPaymentDetailModel;
import com.example.orderservice.model.local.OrderStatusModel;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.OrderStatusRepository;
import com.example.orderservice.service.external.PaymentServiceClient;
import com.example.orderservice.service.external.UserServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    //local
    private final OrderRepository orderRepository;
    //local
    private final OrderStatusRepository orderStatusRepository;
    //external
    private final PaymentServiceClient paymentServiceClient;
    //external
    private final UserServiceClient userServiceClient;

    public String createOrderFallbackMethod(CreateOrderDTO.Request request, Exception exception) throws ServiceException {
        log.error("CreateOrderFallbackMethod : {} ", exception.getMessage());
        throw new ServiceException("CreateOrderFallbackMethod", exception);
    }

    @Transactional
    @CircuitBreaker(name = "createOrder", fallbackMethod = "createOrderFallbackMethod")
    public String createOrder(CreateOrderDTO.Request request) throws UserNotActiveException, ServiceException {
        LocalDateTime now = LocalDateTime.now();
        log.debug("order process started.");
        String orderId = UUID.randomUUID().toString();

        MDC.put("orderId", orderId);
        UserViewModal.UserViewResponse userByName;
        {
            //[user-service|external]check the user is in active mode or not.
            userByName = this.userServiceClient.getUserByName(request.getUsername());
            if (!userByName.isActive()) {
                throw new UserNotActiveException(
                        request.getUsername() + " is not in active mode.",
                        request.getUsername()
                );
            } else {
                log.debug("user is active");
            }
        }


        {
            //[order-service|local] crete the order.
            ArrayList<OrderStatusEntity> status = new ArrayList<>();

            OrderEntity orderEntity = OrderEntity
                    .builder()
                    .orderId(orderId)
                    .orderDateTime(now)
                    .username(request.getUsername())
                    .amount(request.getAmount())
                    .paymentId(null)
//                    .status(status)
                    .build();
            OrderStatusEntity statusEntity = OrderStatusEntity
                    .builder()
                    .orderStatusId(UUID.randomUUID().toString())
                    .updatedDateTime(now)
                    .status("pending")
//                    .orderEntity(orderEntity)
                    .orderId(orderId)
                    .build();

            //[order-service|local] update the order status as 'processing'
            this.orderRepository.save(orderEntity);
            this.orderStatusRepository.save(statusEntity);
            log.debug("the order initialized successfully.");

        }

        {
            //[payment-service|external] make the payment
            NewOrderPaymentModel.Response addedNewOrderPayment = this.paymentServiceClient.addNewOrderPayment(
                    NewOrderPaymentModel
                            .Request
                            .builder()
                            .userId(userByName.getUserId())
                            .orderId(orderId)
                            .amount(request.getAmount())
                            .username(request.getUsername())
                            .build()
            );
            log.debug("payment made successfully.");

            //[order-service|local] update the order status as 'payment-success'
            this.orderStatusRepository.save(
                    OrderStatusEntity
                            .builder()
                            .orderStatusId(UUID.randomUUID().toString())
//                            .orderEntity(OrderEntity.builder().orderId(orderId).build())
                            .orderId(orderId)
                            .updatedDateTime(LocalDateTime.now())
                            .status(addedNewOrderPayment.getStatus())
                            .build()
            );
            log.debug("order status updated [{}].", addedNewOrderPayment.getStatus());
            //update the payment id.
            this.orderRepository.updateTransactionId(
                    addedNewOrderPayment.getOrderPaymentId(),
                    orderId
            );
            log.debug("transaction id was updated in the order table [{}].", addedNewOrderPayment.getOrderPaymentId());

            MDC.clear();
        }
        return orderId;
    }


    //business logic
    public Optional<OrderStatusDTO.Response> getOrderStatus(String orderId) {
        //01. order-service: get the order details [summary] [internal]
        Optional<OrderEntity> order = this.orderRepository.findFirstByOrderId(orderId);
        OrderStatusDTO.Response.ResponseBuilder builder = OrderStatusDTO.Response.builder();
        if (order.isPresent()) {

            {
                builder.orderId(order.get().getOrderId());
                builder.orderDate(order.get().getOrderDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
                builder.orderAmount(order.get().getAmount());
            }
            {
                //02. user-service: get the user details. [external]
                try {
                    UserViewModal.UserViewResponse userByName = this.userServiceClient.getUserByName(order.get().getUsername());
                    builder.username(userByName.getUsername());
                    builder.tel(userByName.getTel());
                    builder.userId(userByName.getUserId());
                    builder.userServiceIsDone(true);
                } catch (ServiceException e) {
                    log.error("ServiceException : {}", e.getCause().getMessage());
                    builder.userServiceIsDone(false);
                }
            }
            {
                //03. order-service: get the order status list. [internal]
                List<OrderStatusModel.Response.OrderStatus> statusList = this.orderStatusRepository.findAllByOrderId(order.get().getOrderId())
                        .stream().map(orderStatusEntity -> OrderStatusModel
                                .Response
                                .OrderStatus
                                .builder()
                                .status(orderStatusEntity.getStatus())
                                .statusDatetime(orderStatusEntity.getUpdatedDateTime().format(DateTimeFormatter.ISO_DATE_TIME))
                                .build()).collect(Collectors.toList()
                        );
                builder.statuses(statusList);
            }
            {
                //04. payment-service: get the payment details. [external]
                try {
                    ViewOrderPaymentDetailModel.Response orderPayment = this.paymentServiceClient.getOrderPayment(order.get().getOrderId());
                    builder.transactionId(orderPayment.getOrderPaymentId());
                    builder.transactionStatus(orderPayment.getStatus());
                    builder.paymentServiceIsDone(true);
                } catch (ServiceException e) {
                    log.error("ServiceException : {}", e.getCause().getMessage());
                    builder.paymentServiceIsDone(false);
                }
            }
            return Optional.of(builder.build());
        } else {
            return Optional.empty();
        }

    }
}
