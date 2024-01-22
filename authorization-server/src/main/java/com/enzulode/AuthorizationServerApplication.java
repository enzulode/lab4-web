package com.enzulode;

import com.enzulode.conf.model.CorsConfigurationProperties;
import com.enzulode.model.KeysConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** This class contains jar-level application entrypoint. */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({KeysConfiguration.class, CorsConfigurationProperties.class})
public class AuthorizationServerApplication {

    /**
     * Jar-level application entrypoint.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }
}
