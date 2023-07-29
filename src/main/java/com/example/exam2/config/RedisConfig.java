package com.example.exam2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Configuration
public class RedisConfig {

    /**
     * 尝试通过redis取出缓存的token，提高效率
     * 通过创建 RedisTemplate 的 Bean，可以在应用程序中方便地使用 Redis 进行数据存储和检索
     * 以下配置：
     * 1.设置 Redis 连接工厂：通过 setConnectionFactory() 方法，将 RedisConnectionFactory 注入到 RedisTemplate 中，以便建立与 Redis 的连接。
     * 2.设置键的序列化器：通过 setKeySerializer() 方法，将键的序列化器设置为 StringRedisSerializer，这样在 Redis 中存储的键将以字符串的形式进行序列化和反序列化。
     * 3.设置值的序列化器：通过 setValueSerializer() 方法，将值的序列化器设置为 GenericJackson2JsonRedisSerializer，这样在 Redis 中存储的值将以 JSON 格式进行序列化和反序列化。
     * 通过上述配置，可以使用 RedisTemplate 对象进行各种操作，如存储字符串、存储对象、读取数据等。
     * 例如 可以使用 opsForValue() 方法来获取值操作的操作对象，然后使用 set() 方法将值存储到 Redis 中，使用 get() 方法获取存储在 Redis 中的值。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
