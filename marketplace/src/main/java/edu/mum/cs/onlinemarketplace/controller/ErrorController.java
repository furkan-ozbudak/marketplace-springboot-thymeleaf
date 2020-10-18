package edu.mum.cs.onlinemarketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "errorAuthorization";
    }

    @RequestMapping(value = "/server-error", method = RequestMethod.GET)
    public String serverError() {
        return "errorCommon";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound() {
        return "error404";
    }
}
