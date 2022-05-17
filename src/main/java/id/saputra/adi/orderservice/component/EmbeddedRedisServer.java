package id.saputra.adi.orderservice.component;

import com.github.fppt.jedismock.RedisServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class EmbeddedRedisServer {
    @Value("${spring.redis.port}")
    private int port;
    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        redisServer = RedisServer.newRedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() throws IOException {
        redisServer.stop();
    }

}
