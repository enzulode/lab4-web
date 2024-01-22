package com.enzulode.conf.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** This class encapsulates cloud gateway CORS-related properties. */
@Getter
@Setter
@ConfigurationProperties("gateway.cors")
public class CorsConfigurationProperties {

    /** A list of CORS properties. */
    List<CorsConfig> configs;

    /**
     * This record encapsulates single CORS policy properties set.
     *
     * @param allowedOriginPatterns allowed origin patterns
     * @param allowCredentials are credentials (e.g. cookies) allowed or not
     * @param allowedMethods allowed HTTP methods
     * @param allowedHeaders allowed HTTP headers
     * @param maxAge allowed time (in seconds) request might be cached by clients
     */
    public record CorsConfig(
            String mappingPattern,
            List<String> allowedOriginPatterns,
            Boolean allowCredentials,
            List<String> allowedMethods,
            List<String> allowedHeaders,
            Long maxAge) {}
}
