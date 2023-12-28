package com.enzulode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** This class contains jar-level application entrypoint. */
@SpringBootApplication
public class IdentityProviderApplication {

    /**
     * Jar-level application entrypoint.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(IdentityProviderApplication.class, args);
    }
}
