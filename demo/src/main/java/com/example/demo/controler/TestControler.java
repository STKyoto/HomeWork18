package com.example.demo.controler; // Исправлено название пакета

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class TestControler {

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}
