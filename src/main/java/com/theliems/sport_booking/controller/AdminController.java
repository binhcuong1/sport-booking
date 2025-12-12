package com.theliems.sport_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String dashboard(Model model) {

        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("active", "dashboard");
        model.addAttribute("pageContent", "admin/dashboard");

        return "admin/layouts/admin_layout";
    }
}



