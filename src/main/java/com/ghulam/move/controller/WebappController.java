package com.ghulam.move.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebappController {

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }
}
