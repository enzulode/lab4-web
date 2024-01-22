package com.enzulode;

import com.enzulode.config.model.CorsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** This class contains jar-level application entrypoint. */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(CorsConfigurationProperties.class)
public class DevResourceServerApplication {

    /**
     * Jar-level application entrypoint.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DevResourceServerApplication.class, args);
    }
}
