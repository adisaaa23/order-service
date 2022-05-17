package id.saputra.adi.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.saputra.adi.orderservice.domain.dto.*;
import id.saputra.adi.orderservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class OrderService implements IOrder {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CacheManagerService cacheManagerService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private boolean isEligibleOrder(String cusRisk, String productRisk) {
        return Integer.parseInt(cusRisk) >= Integer.parseInt(productRisk);
    }

    @Override
    public OrderDtoRs inquiry(OrderDtoRq orderDtoRq) {
        log.info("Starting to inquiry order ....");
        String referenceNumber = String.valueOf(new Date().getTime());
        try {
            /* Call Data Customer Detail */
            CustomerDto customerDto = customerService.detailCustomer(orderDtoRq.getUsername());
            if (Objects.isNull(customerDto)) {
                return new OrderDtoRs("101", "Customer invalid!", referenceNumber);
            }
            /* Call Data Product Detail */
            ProductDto productDto = productService.detailProduct(orderDtoRq.getProductCode());
            if (Objects.isNull(productDto)) {
                return new OrderDtoRs("102", "Product invalid!", referenceNumber);
            }
            /* Cek Eligible Order */
            log.info("Check eligible order");
            if (!isEligibleOrder(customerDto.getRiskProfileCode(), productDto.getRiskProfile())) {
                return new OrderDtoRs("103", "Product not eligible for your order!", referenceNumber);
            }

            TransactionDto transactionDto = TransactionDto.builder()
                    .referenceNo(referenceNumber)
                    .amount(orderDtoRq.getAmount())
                    .productCode(productDto.getCode())
                    .productName(productDto.getName())
                    .riskProfile(customerDto.getRiskProfileCode())
                    .username(customerDto.getUsername())
                    .build();

            String jsonTransaction = new ObjectMapper().writeValueAsString(transactionDto);
            cacheManagerService.putCache("transactions", customerDto.getUsername() + transactionDto.getReferenceNo(), jsonTransaction, 600);
            log.info("Success inquiry order ...");
            return new OrderDtoRs("000", "Inquiry Order Successfully", referenceNumber);
        } catch (Exception exception) {
            log.error("Happened error when inquiry order : ", exception);
            throw new ApplicationException("UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OrderDtoRs order(OrderDtoRq orderDtoRq) {
        log.info("Starting to processing order ....");
        TransactionDto transactionDto = new TransactionDto();
        try {
            String keyCache = orderDtoRq.getUsername() + orderDtoRq.getReferenceNumber();
            String cacheTransaction = (String) cacheManagerService.getCache("transactions", keyCache);

            if (Objects.isNull(cacheTransaction)){
                return new OrderDtoRs("0101", "Transaction Invalid!", orderDtoRq.getReferenceNumber());
            }
            transactionDto = objectMapper.readValue(cacheTransaction, TransactionDto.class);
            transactionDto.setTransactionStatus("SUCCESS");
            transactionDto.setTransactionDesc("Order Successfully");
            log.info("Success inquiry order ...");
            cacheManagerService.removeCache("transactions", keyCache);
            return new OrderDtoRs("000", "Processing Order Successfully", orderDtoRq.getReferenceNumber());
        } catch (Exception exception) {
            log.error("Happened error when processing order : ", exception);
            transactionDto.setTransactionStatus("FAILED");
            transactionDto.setTransactionDesc("Order Failed " + exception.getMessage());
            throw new ApplicationException("UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            kafkaProducerService.publishMessage(transactionDto);
        }
    }
}
