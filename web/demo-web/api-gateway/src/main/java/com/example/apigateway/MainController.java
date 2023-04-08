package com.example.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * @return get all registered services from the local cache from eureka-client (eureka-server)
     */
    @GetMapping("/services")
    public List<?> getAllServices() {
        return this.discoveryClient.getServices();
    }

    @GetMapping("/instance/{service-name}")
    public List<?> getAllInstances(@PathVariable("service-name") String service) {
        return this.discoveryClient.getInstances(service)
                .stream()
                .map(serviceInstance -> {
                    return serviceInstance.getInstanceId() + ":" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "";
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{prefix}/{restURL:[a-z]+}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object collectAndProcess(
            @PathVariable String prefix,
            @PathVariable String restURL,
            HttpServletRequest request,
            HttpEntity<Object> requestData
    ) {
        Optional<String> service = this.discoveryClient.getServices()
                .stream()
                .filter(serviceName -> serviceName.equals(prefix))
                .findFirst();

        if (service.isPresent()) {
            int skipItem = new Random().nextInt(this.discoveryClient.getInstances(service.get()).size());
            System.out.println("skipItem = " + skipItem);
            UriComponents donStreamPath = this.discoveryClient
                    .getInstances(service.get())
                    .stream()
                    .skip(skipItem)
                    .findFirst()
                    .map(serviceInstance -> {

                        //identified the down-stream
                        UriComponents path = UriComponentsBuilder
                                .newInstance()
                                .scheme("http")
                                .host(serviceInstance.getHost())
                                .port(serviceInstance.getPort())
                                .path(restURL)
                                .build();
                        System.out.println("path: " + path);
                        return path;
                    }).get();
            //call to the down-stream
            //http://localhost:63303/test
            HttpHeaders httpHeadersForDS = new HttpHeaders();
            requestData.getHeaders().forEach((s, strings) -> {
                httpHeadersForDS.add(s, strings.get(0));
            });
            httpHeadersForDS.add("X-Gateway-In", LocalDateTime.now().toString());
            HttpEntity<Object> newRequestData = new HttpEntity<>(requestData.getBody(), httpHeadersForDS);

            System.out.println("URL:" + donStreamPath.toUri());
            ResponseEntity<Object> exchange = new RestTemplate()
                    .exchange(
                            donStreamPath.toUri(),
                            HttpMethod.resolve(request.getMethod()),
                            newRequestData,
                            Object.class
                    );

            HttpHeaders httpHeaders = new HttpHeaders();
            exchange.getHeaders().forEach((s, strings) -> {
                httpHeaders.add(s, strings.get(0));
            });
            httpHeaders.add("X-gateway-name", applicationName);

            ResponseEntity<Object> objectResponseEntity
                    = new ResponseEntity<>(exchange.getBody(), httpHeaders, exchange.getStatusCode());

            return objectResponseEntity;
        } else {
            return "server is not registered with eureka service yet.";
        }
    }
}
