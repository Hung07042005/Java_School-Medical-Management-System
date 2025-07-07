// src/main/java/com/example/demo/controller/HomeController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Trả về template home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về template login.html
    }
}