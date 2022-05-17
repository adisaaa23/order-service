package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.ProductDto;
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
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${url.data.product.detail}")
    private String urlDataPrdDetail;

    @Test
    public void detailCustomerExpectSuccess(){
        when(restTemplate.getForEntity(eq(urlDataPrdDetail), eq(ProductDto.class), anyString())).thenReturn(
                ResponseEntity.ok(ProductDto.builder().code("adis").build())
        );
        ProductDto productDto = productService.detailProduct("adis");
        assertEquals("adis", productDto.getCode());
    }

    @Test
    public void detailCustomerExpectNull(){
        when(restTemplate.getForEntity(eq(urlDataPrdDetail), eq(ProductDto.class), anyString())).thenReturn(
                ResponseEntity.ok(null)
        );
        ProductDto productDto = productService.detailProduct("adis");
        assertNull(productDto);
    }
}
