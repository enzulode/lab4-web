package com.enzulode.client;

import com.enzulode.conf.security.SecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller allows to substitute the default spring security authentication form with the
 * custom from static resources.
 */
@Controller
public class UIController {

    /**
     * This endpoint is responsible for login page.
     *
     * @return login page 'MVC view'
     */
    @GetMapping(SecurityConfig.LOGIN_PAGE_URL)
    public String index() {
        return "index";
    }
}
