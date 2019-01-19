package ru.rsoi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.rsoi.gateway.config.TrackingFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableWebSecurity
public class GatewayApplication {



    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(GatewayApplication.class, args);

    }
    @Bean
    public TrackingFilter trackingFilter() {
        return new TrackingFilter();
    }
}
