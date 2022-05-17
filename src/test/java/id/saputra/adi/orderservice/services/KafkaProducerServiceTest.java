package id.saputra.adi.orderservice.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9098", "port=9098"})
public class KafkaProducerServiceTest {
   /* @Autowired
    private KafkaProducerService kafkaProducerService;

    private final CountDownLatch latch = new CountDownLatch(1);

    private String payload;

    @Test
    public void publishAuditTrailMsgNullObjectTests() throws InterruptedException {
        kafkaProducerService.publishAuditTrailMsg(null);
        var await = getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info("await: {}", await);
        assertEquals(1, getLatch().getCount());
    }

    @Test
    public void publishMessagingAuditTrailNullObjectTests() throws InterruptedException {
        kafkaProducerService.publishMessagingAuditTrail(null);
        var await = getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info("await: {}", await);
        assertEquals(1, getLatch().getCount());
    }

    @Test
    public void publishAuditTrailMsgTests() throws InterruptedException, JsonProcessingException {
        var msg = CustomerActivityDTO.builder()
                .id("1")
                .customerId(1)
                .activityName(Constant.ActivityName.ACTIVITY_INQUIRY_FT_SWIFT)
                .moduleName("TRANSFER")
                .channelGroup("")
                .octomobileRefNo("RF998349389434")
                .sourceError("")
                .errorCode("0")
                .ipAddress("127.0.0.1")
                .deviceUniqueId("123456789")
                .deviceInfo("iOS")
                .channelCode("001")
                .status(0)
                .errorDesc("SUCCESS")
                .build();

        var publishedMsg = new ObjectMapper().writeValueAsString(msg);

        kafkaProducerService.publishAuditTrailMsg(msg);
        receiveCustomerActivity(msg);
        var await = getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info("await: {}", await);
        assertEquals(0, getLatch().getCount());
        assertThat(getPayload()).contains(publishedMsg);
    }

    @Test
    public void publishMessagingAuditTrailTests() throws InterruptedException, JsonProcessingException {
        var msg = MessageAuditTrailDTO.builder()
                .rqId("123")
                .customerAuditTrailId("squad1ios")
                .errorCode("0")
                .errorDescription("SUCCESS")
                .response("{}")
                .requestTime(new Date())
                .responseTime(new Date())
                .request("{}").build();

        var publishedMsg = new ObjectMapper().writeValueAsString(msg);

        kafkaProducerService.publishMessagingAuditTrail(msg);
        receiveMessagingAuditTrail(msg);
        var await = getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info("await: {}", await);
        assertEquals(0, getLatch().getCount());
        assertThat(getPayload()).contains(publishedMsg);
    }

    @KafkaListener(topics = "SAVE_AUDIT_TRAIL")
    public void receiveCustomerActivity(CustomerActivityDTO consumerRecord) throws JsonProcessingException {
        log.info("received payload='{}'", consumerRecord.toString());
        setPayload(new ObjectMapper().writeValueAsString(consumerRecord));
        latch.countDown();
    }

    @KafkaListener(topics = "SAVE_MESSAGE_AUDIT_TRAIL")
    public void receiveMessagingAuditTrail(MessageAuditTrailDTO consumerRecord) throws JsonProcessingException {
        log.info("received payload='{}'", consumerRecord.toString());
        setPayload(new ObjectMapper().writeValueAsString(consumerRecord));
        latch.countDown();
    }

    private CountDownLatch getLatch() {
        return latch;
    }

    private String getPayload() {
        return payload;
    }

    private void setPayload(String payload) {
        this.payload = payload;
    }*/
}
