package edu.mum.cs.onlinemarketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavicoController {

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {}
}
