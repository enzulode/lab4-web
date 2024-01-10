package com.enzulode.controller;

import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** This controller is made for development purposes only. */
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
public class DevController {

    /**
     * This method returns simple greeting to the authenticated user.
     *
     * @return greeting
     */
    @GetMapping("/hello")
    public String helloEndpoint() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return String.format("Hello, %s", principal.getName());
    }
}
