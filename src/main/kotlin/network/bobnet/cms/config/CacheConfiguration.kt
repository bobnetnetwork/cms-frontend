package network.bobnet.cms.config

import com.google.common.base.Joiner.on
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfiguration(
        @Autowired val redisConnectionFactory: RedisConnectionFactory,
        @Value("\${spring.cache.time-to-live:60000}") val ttl: Long
) : CachingConfigurerSupport() {

    private val logger = LoggerFactory.getLogger(CacheConfiguration::class.java)

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMillis(ttl))
    }

    @Bean
    override fun cacheManager(): CacheManager {
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .transactionAware()
                .build()
    }

    @Bean
    override fun keyGenerator(): KeyGenerator {
        return KeyGenerator { target, method, params ->
            val key = "%s.%s(%s)".format(target::class.qualifiedName,
                    method.name,
                    on(",").skipNulls().join(params))
            logger.debug("Generating key: '{}' in order to cache class '{}' instance", key, method.returnType.name)
            return@KeyGenerator key
        }
    }

    @Bean
    override fun errorHandler(): CacheErrorHandler {
        return object : CacheErrorHandler {
            override fun handleCacheClearError(exception: java.lang.RuntimeException, cache: Cache) {
                logger.warn("Error on clear cache: '{}'", exception.message)
            }

            override fun handleCachePutError(exception: java.lang.RuntimeException, cache: Cache, key: Any, value: Any?) {
                logger.warn("Error on put value in cache: '{}'", exception.message)
            }

            override fun handleCacheEvictError(exception: java.lang.RuntimeException, cache: Cache, key: Any) {
                logger.warn("Error on evict cache value: '{}'", exception.message)
            }

            override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
                logger.warn("Error on get cache value: '{}'", exception.message)
            }
        }
    }
}
