package com.ambrose.tripwonder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
public class TripWonderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripWonderApplication.class, args);
    }

}
