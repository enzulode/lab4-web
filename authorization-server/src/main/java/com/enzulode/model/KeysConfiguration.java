package com.enzulode.model;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/** This class contains security keys configurations. */
@ConfigurationProperties("spring.security.keys")
@Getter
@Setter
public class KeysConfiguration {

    /** A list of configured rsa key-pairs. */
    @NestedConfigurationProperty private List<RSAKeyPair> rsa;

    /** This class represents RSA key-pair. */
    @Getter
    @Setter
    public static class RSAKeyPair {

        /** Key id. */
        private String id;

        /** RSA private key in pkcs8 format. */
        private RSAPrivateKey privateKey;

        /** RSA public key in pkcs8 format. */
        private RSAPublicKey publicKey;
    }
}
