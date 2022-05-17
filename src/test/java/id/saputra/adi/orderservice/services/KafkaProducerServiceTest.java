package id.saputra.adi.orderservice.services;

import id.saputra.adi.orderservice.domain.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9098", "port=9098"})
public class KafkaProducerServiceTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.transaction}")
    private String topicTransaction;

    @Test
    public void publishMessageExpectSuccess() throws JsonProcessingException {
        TransactionDto transactionDto = TransactionDto.builder()
                .referenceNo("XXXX")
                .build();
        kafkaProducerService.publishMessage(transactionDto);
        verify(kafkaTemplate, times(1)).send(topicTransaction, new ObjectMapper().writeValueAsString(transactionDto));
    }

    @Test
    public void publishMessageExpectException() throws JsonProcessingException {
        TransactionDto transactionDto = TransactionDto.builder()
                .referenceNo("XXXX")
                .build();
        when(kafkaTemplate.send(any(), any())).thenThrow(new KafkaException(""));
        kafkaProducerService.publishMessage(transactionDto);
        verify(kafkaTemplate, times(1)).send(topicTransaction, new ObjectMapper().writeValueAsString(transactionDto));
    }

}
