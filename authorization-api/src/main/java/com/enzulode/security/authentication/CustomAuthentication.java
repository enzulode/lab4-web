package com.enzulode.security.authentication;

import org.springframework.security.core.Authentication;

/**
 * This interface represents authentication stored in the security context after authentication
 * process.
 */
public interface CustomAuthentication extends Authentication {

    /**
     * Get credentials method retrieves user password. Expected to return empty string all the time.
     *
     * @return empty string
     */
    @Override
    default Object getCredentials() {
        return "";
    }

    /**
     * Details setter
     *
     * @param details details to be set
     */
    void setDetails(Object details);
}
