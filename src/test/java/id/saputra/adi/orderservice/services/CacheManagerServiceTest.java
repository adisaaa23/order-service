package id.saputra.adi.orderservice.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheManagerServiceTest {

    @Autowired
    private CacheManagerService cacheManagerService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void putCacheTest(){
        cacheManagerService.putCache("CACHE_NAME", "key", "data", 60);
        assertEquals("data", (String) cacheManagerService.getCache("CACHE_NAME", "key"));
    }

    @Test
    public void getCacheTest(){
        cacheManagerService.putCache("CACHE_NAME", "key", "data", 60);
        assertEquals("data", (String) cacheManagerService.getCache("CACHE_NAME", "key"));
    }

    @Test
    public void removeCacheTest(){
        cacheManagerService.putCache("CACHE_NAME", "key", "data", 60);
        cacheManagerService.removeCache("CACHE_NAME", "key");
        assertNull(cacheManagerService.getCache("CACHE_NAME", "key"));
    }

}
