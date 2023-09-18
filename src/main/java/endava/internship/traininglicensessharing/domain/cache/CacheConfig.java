package endava.internship.traininglicensessharing.domain.cache;

import static endava.internship.traininglicensessharing.domain.cache.CacheContext.AVERAGE_COSTS_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.COST_DATA_OVERVIEW_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.LICENSES_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.LICENSE_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUESTS_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.REQUEST_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.ROLES_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.USERS_CACHE;
import static endava.internship.traininglicensessharing.domain.cache.CacheContext.USERS_OVERVIEW_CACHE;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class CacheConfig {

    @Bean("cacheKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

    @ConditionalOnProperty(name = "cache.type", havingValue = "caffeine")
    static class CaffeineCacheConfig {

        @Bean
        public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
            cacheManager.setCaffeine(Caffeine.newBuilder().recordStats());
            cacheManager.setCacheNames(getCacheNames());
            cacheManager.registerCustomCache(LICENSES_CACHE, licensesCache().getNativeCache());
            cacheManager.registerCustomCache(LICENSE_CACHE, licenseCache().getNativeCache());
            cacheManager.registerCustomCache(USERS_CACHE, usersCache().getNativeCache());
            cacheManager.registerCustomCache(USERS_OVERVIEW_CACHE, usersOverviewCache().getNativeCache());
            cacheManager.registerCustomCache(AVERAGE_COSTS_OVERVIEW_CACHE, averageCostsOverviewCache().getNativeCache());
            cacheManager.registerCustomCache(COST_DATA_OVERVIEW_CACHE, costDataOverviewCache().getNativeCache());
            cacheManager.registerCustomCache(ROLES_CACHE, rolesCache().getNativeCache());
            cacheManager.registerCustomCache(REQUESTS_CACHE, requestsCache().getNativeCache());
            cacheManager.registerCustomCache(REQUEST_CACHE, requestCache().getNativeCache());

            return cacheManager;
        }

        private List<String> getCacheNames() {
            return Arrays.asList(
                    LICENSES_CACHE,
                    LICENSE_CACHE,
                    USERS_CACHE,
                    USERS_OVERVIEW_CACHE,
                    AVERAGE_COSTS_OVERVIEW_CACHE,
                    COST_DATA_OVERVIEW_CACHE,
                    ROLES_CACHE,
                    REQUESTS_CACHE,
                    REQUEST_CACHE
            );
        }

        @Bean
        public CaffeineCache  licensesCache() {
            return new CaffeineCache(LICENSES_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(1, TimeUnit.HOURS)
                    .build());
        }

        @Bean
        public CaffeineCache licenseCache() {
            return new CaffeineCache(LICENSE_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(1, TimeUnit.HOURS)
                    .build());
        }

        @Bean
        public CaffeineCache usersCache() {
            return new CaffeineCache(USERS_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache requestsCache() {
            return new CaffeineCache(REQUESTS_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache requestCache() {
            return new CaffeineCache(REQUEST_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache usersOverviewCache() {
            return new CaffeineCache(USERS_OVERVIEW_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache averageCostsOverviewCache() {
            return new CaffeineCache(AVERAGE_COSTS_OVERVIEW_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache costDataOverviewCache() {
            return new CaffeineCache(COST_DATA_OVERVIEW_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build());
        }

        @Bean
        public CaffeineCache rolesCache() {
            return new CaffeineCache(ROLES_CACHE, Caffeine.newBuilder()
                    .expireAfterAccess(1, TimeUnit.HOURS)
                    .build());
        }

    }

    @ConditionalOnProperty(name = "cache.type", havingValue = "hashmap")
    static class HashMapCacheConfig {

        @CacheEvict(allEntries = true, value = {
                LICENSES_CACHE,
                LICENSE_CACHE,
                USERS_CACHE,
                USERS_OVERVIEW_CACHE,
                AVERAGE_COSTS_OVERVIEW_CACHE,
                COST_DATA_OVERVIEW_CACHE,
                REQUESTS_CACHE,
                REQUEST_CACHE,
                ROLES_CACHE
        })
        @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 500)
        public void reportCacheEvict() {
            log.info("Flush Cache " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS").format(new Date()));
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(
                    LICENSES_CACHE,
                    LICENSE_CACHE,
                    USERS_CACHE,
                    USERS_OVERVIEW_CACHE,
                    AVERAGE_COSTS_OVERVIEW_CACHE,
                    COST_DATA_OVERVIEW_CACHE,
                    ROLES_CACHE,
                    REQUESTS_CACHE,
                    REQUEST_CACHE
            );
        }
    }
}
