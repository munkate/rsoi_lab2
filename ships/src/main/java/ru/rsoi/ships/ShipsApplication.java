package ru.rsoi.ships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)*/
public class ShipsApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ShipsApplication.class, args);
    }
}
