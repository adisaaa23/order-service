package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ProductService {

    @Value("${url.data.product.detail}")
    private String urlDataPrdDetail;
    @Autowired
    private RestTemplate restTemplate;

    public ProductDto detailProduct(String productCode){
        log.info("Starting get detail product");
        ResponseEntity<ProductDto> responseEntity = restTemplate.getForEntity(urlDataPrdDetail, ProductDto.class, productCode);
        if (responseEntity.hasBody()){
            ProductDto productDto = responseEntity.getBody();
            log.debug("Data product detail {}", productDto);
            return productDto;
        }
        log.warn("Data product not found!");
        return null;
    }
}
