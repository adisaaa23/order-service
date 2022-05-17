package id.saputra.adi.orderservice.config;

import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisTemplate<String, Object> redisTemplate (LettuceConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory (){
        var redisConfiguration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(redisConfiguration, LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(5))
                .clientResources(ClientResources.builder().build())
                .build());
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("customers",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(90)));
    }

    @Bean("customerKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new RedisKeyGenerator();
    }

    static class RedisKeyGenerator implements KeyGenerator {

        @Override
        public Object generate(Object o, Method method, Object... objects) {
            return StringUtils.arrayToDelimitedString(objects, "_").hashCode();
        }
    }
}
