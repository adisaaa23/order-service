package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.*;
import id.saputra.adi.orderservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ProductService productService;

    @MockBean
    private CacheManagerService cacheManagerService;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    @Test
    public void inquiryExpectSuccess() {
        when(customerService.detailCustomer(any())).thenReturn(CustomerDto.builder().riskProfileCode("3").build());
        when(productService.detailProduct(any())).thenReturn(ProductDto.builder().riskProfile("1").build());
        doNothing().when(cacheManagerService).putCache(anyString(), anyString(), anyString(), anyLong());
        OrderDtoRs orderDtoRs = orderService.inquiry(OrderDtoRq.builder()
                .build());
        assertEquals("000", orderDtoRs.getErrorCode());
    }

    @Test
    public void inquiryExpectNoEligibleOrder() {
        when(customerService.detailCustomer(any())).thenReturn(CustomerDto.builder().riskProfileCode("1").build());
        when(productService.detailProduct(any())).thenReturn(ProductDto.builder().riskProfile("3").build());
        doNothing().when(cacheManagerService).putCache(anyString(), anyString(), anyString(), anyLong());
        OrderDtoRs orderDtoRs = orderService.inquiry(OrderDtoRq.builder()
                .build());
        assertEquals("103", orderDtoRs.getErrorCode());
    }

    @Test
    public void inquiryExpectCusNotFound() {
        when(customerService.detailCustomer(any())).thenReturn(null);
        OrderDtoRs orderDtoRs = orderService.inquiry(OrderDtoRq.builder()
                .build());
        assertEquals("101", orderDtoRs.getErrorCode());
    }

    @Test
    public void inquiryExpectProductInvalid() {
        when(customerService.detailCustomer(any())).thenReturn(CustomerDto.builder().riskProfileCode("1").build());
        when(productService.detailProduct(any())).thenReturn(null);
        OrderDtoRs orderDtoRs = orderService.inquiry(OrderDtoRq.builder()
                .build());
        assertEquals("102", orderDtoRs.getErrorCode());
    }

    @Test(expected = ApplicationException.class)
    public void inquiryExpectException() {
        when(customerService.detailCustomer(any())).thenThrow(ResourceAccessException.class);
        orderService.inquiry(OrderDtoRq.builder()
                .build());
    }

    @Test
    public void orderExpectSuccess() throws JsonProcessingException {
        when(cacheManagerService.getCache(eq("transactions"), anyString()))
                .thenReturn(new ObjectMapper().writeValueAsString(TransactionDto.builder().build()));
        doNothing().when(kafkaProducerService).publishMessage(any());
        OrderDtoRs orderDtoRs = orderService.order(OrderDtoRq.builder().build());
        assertEquals("000", orderDtoRs.getErrorCode());
    }

    @Test
    public void orderExpectTransactionInvalid() {
        when(cacheManagerService.getCache(eq("transactions"), anyString()))
                .thenReturn(null);
        doNothing().when(kafkaProducerService).publishMessage(any());
        OrderDtoRs orderDtoRs = orderService.order(OrderDtoRq.builder().build());
        assertEquals("0101", orderDtoRs.getErrorCode());
    }

    @Test(expected = ApplicationException.class)
    public void orderExpectException() {
        when(cacheManagerService.getCache(eq("transactions"), anyString()))
                .thenThrow(NullPointerException.class);
        doNothing().when(kafkaProducerService).publishMessage(any());
        orderService.order(OrderDtoRq.builder().build());
    }
}
