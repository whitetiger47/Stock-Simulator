package com.project.stocktrading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author abhishekuppe
 */

@Controller
public class HomePageController {

    @GetMapping("/")
    public String CSVUploadHome() {
        return "redirect:/watchlisthome/";
    }
}
