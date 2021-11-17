package com.example.qwer

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ControllerLogin {
    @GetMapping("/login")
    fun auth(): String {
        return "auth"
    }
}