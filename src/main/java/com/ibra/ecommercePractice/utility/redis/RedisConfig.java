package com.ibra.ecommercePractice.utility.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
//@EnableCaching
public class RedisConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
            RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(10)) // Set TTL
                    .disableCachingNullValues();
            return RedisCacheManager.builder(redisConnectionFactory)
                    .cacheDefaults(cacheConfiguration)
                    .build();
        }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
////        // Use String serializer for keys
////        template.setKeySerializer(new StringRedisSerializer());
////
////        // Use Jackson JSON serializer for values
////        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
////        template.setEnableTransactionSupport(true);
////
////        return template;
//        // Configure ObjectMapper with proper modules
//        ObjectMapper mapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        GenericJackson2JsonRedisSerializer jsonSerializer =
//                new GenericJackson2JsonRedisSerializer(mapper);
//
//        template.setDefaultSerializer(jsonSerializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jsonSerializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jsonSerializer);
//
//        return template;
//    }
//
//    @Bean
//    public GenericJackson2JsonRedisSerializer jacksonSerializer() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(
//                objectMapper.getPolymorphicTypeValidator(),
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                JsonTypeInfo.As.WRAPPER_ARRAY
//        );
//
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
