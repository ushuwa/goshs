package com.example.strandrecommender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/questionnaire")
    public String questionnairePage() {
        return "questionnaire";
    }
}
