package ru.astondevs.asber.infoservice.config;

import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config class for spring cache.
 */
@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * Cache manager bean setting. Here we can set cache names.
     * @return Configured {@link CacheManager}.
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager();
        concurrentMapCacheManager.setCacheNames(List.of("bankBranches"));
        return concurrentMapCacheManager;
    }

}
