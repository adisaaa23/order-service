package id.saputra.adi.orderservice.controller;

import id.saputra.adi.orderservice.domain.dto.OrderDtoRs;
import id.saputra.adi.orderservice.exception.ApplicationException;
import id.saputra.adi.orderservice.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void inquiryExpectSuccess() throws Exception {
        when(orderService.inquiry(any())).thenReturn(OrderDtoRs.builder().build());
        this.mockMvc.perform(post("/orders/inquiry")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void inquiryExpectException() throws Exception {
        when(orderService.inquiry(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(post("/orders/inquiry")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void orderExpectSuccess() throws Exception {
        when(orderService.order(any())).thenReturn(OrderDtoRs.builder().build());
        this.mockMvc.perform(post("/orders/create")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void orderExpectException() throws Exception {
        when(orderService.order(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(post("/orders/create")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

}
