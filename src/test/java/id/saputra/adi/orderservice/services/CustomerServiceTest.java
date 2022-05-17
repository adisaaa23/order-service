package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${url.data.customer.detail}")
    private String urlDataCusDetail;

    @Test
    public void detailCustomerExpectSuccess(){
        when(restTemplate.getForEntity(eq(urlDataCusDetail), eq(CustomerDto.class), anyString())).thenReturn(
                ResponseEntity.ok(CustomerDto.builder().username("adis").build())
        );
        CustomerDto customerDto = customerService.detailCustomer("adis");
        assertEquals("adis", customerDto.getUsername());
    }

    @Test
    public void detailCustomerExpectNull(){
        when(restTemplate.getForEntity(eq(urlDataCusDetail), eq(CustomerDto.class), anyString())).thenReturn(
                ResponseEntity.ok(null)
        );
        CustomerDto customerDto = customerService.detailCustomer("adis");
        assertNull(customerDto);
    }
}
