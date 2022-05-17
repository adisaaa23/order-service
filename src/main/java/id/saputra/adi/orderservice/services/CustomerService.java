package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CustomerService {

    @Value("${url.data.customer.detail}")
    private String urlDataCusDetail;
    @Autowired
    private RestTemplate restTemplate;

    public CustomerDto detailCustomer(String username){
        log.info("Starting get detail customer");
        ResponseEntity<CustomerDto> responseEntity = restTemplate.getForEntity(urlDataCusDetail, CustomerDto.class, username);
        if (responseEntity.hasBody()){
            CustomerDto customerDto = responseEntity.getBody();
            log.debug("Data customer detail {}", customerDto);
            return customerDto;
        }
        log.warn("Data customer not found!");
        return null;
    }

}
