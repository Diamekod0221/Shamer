package com.lolshame.LoLShame.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/shamer")
public class StaticServeController {

    @GetMapping(path = "/")
    public String serveWelcomePage(){
        return "welcome";
    }

    @GetMapping(path = "/help")
    public String serveHelpPage(HttpServletRequest request){
        return "help";
    }

    @GetMapping(path = "/error")
    public String errorPage(){
        return "bad-request";
    }

    @GetMapping(path ="/disclaimer")
    public String serveDisclaimer(){return "disclaimer";}

}
