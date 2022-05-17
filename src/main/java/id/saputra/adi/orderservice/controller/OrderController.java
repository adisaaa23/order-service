package id.saputra.adi.orderservice.controller;

import id.saputra.adi.orderservice.domain.dto.OrderDtoRq;
import id.saputra.adi.orderservice.services.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Api(tags = "Customer Services API", value = "Created by Adi Saputra")
@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final String PREFIX_TRACE_ERROR = "Trace error : ";
    
    @Autowired
    private OrderService orderService;

    @PostMapping("/inquiry")
    @ResponseBody
    public ResponseEntity<Object> inquiry (@RequestBody OrderDtoRq orderDtoRq){
        try {
            Object response = orderService.inquiry(orderDtoRq);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex){
            log.error("Happened error when inquiry order. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDtoRq);
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create (@RequestBody OrderDtoRq orderDtoRq){
        try {
            Object response = orderService.order(orderDtoRq);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex){
            log.error("Happened error when create order. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDtoRq);
        }
    }
}
