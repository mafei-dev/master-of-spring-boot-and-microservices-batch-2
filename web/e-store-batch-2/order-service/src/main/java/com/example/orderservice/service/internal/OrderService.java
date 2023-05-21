package com.example.orderservice.service.internal;

import com.example.orderservice.dto.CreateOrderDTO;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.entity.OrderStatusEntity;
import com.example.orderservice.exception.UserNotActiveException;
import com.example.orderservice.model.external.NewOrderPaymentModel;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.OrderStatusRepository;
import com.example.orderservice.service.external.PaymentServiceClient;
import com.example.orderservice.service.external.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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


    @Transactional
    public String createOrder(CreateOrderDTO.Request request) throws UserNotActiveException {
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
                    .status(status)
                    .build();
            status.add(OrderStatusEntity
                    .builder()
                    .orderStatusId(UUID.randomUUID().toString())
                    .updatedDateTime(now)
                    .status("pending")
                    .orderEntity(orderEntity)
                    .build()
            );
            //[order-service|local] update the order status as 'processing'
            this.orderRepository.save(orderEntity);
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
                            .orderEntity(OrderEntity.builder().orderId(orderId).build())
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

}
