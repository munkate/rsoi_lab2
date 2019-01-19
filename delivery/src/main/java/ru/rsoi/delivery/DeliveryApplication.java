package ru.rsoi.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPool;
import ru.rsoi.delivery.consumer.Consumer;

@SpringBootApplication
public class DeliveryApplication {

    public static void main(String[] args) {
       /* JedisPool jedisPool = new JedisPool("127.0.0.1",6379);

        Consumer consumer = new Consumer(jedisPool);


        new Thread(consumer).start();*/
        SpringApplication.run(DeliveryApplication.class, args);

    }

}
