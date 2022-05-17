package id.saputra.adi.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.transaction}")
    private String topicTransaction;

    public void publishMessage(Object message) {
        try {
            String publishMessage = new ObjectMapper().writeValueAsString(message);
            log.debug("Send Data to Topic {} : {}", topicTransaction, publishMessage);
            new Thread(() -> kafkaTemplate.send(topicTransaction, publishMessage)).start();
        } catch (Exception e) {
            log.error("Error when publish to topic {} : {}", topicTransaction, e.getMessage());
            log.trace("Error when publish to topic {} :", topicTransaction, e);
        }
    }
}

