package org.music_player.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping()
    public String index() {
        return "redirect:/admin/";
    }
    @RequestMapping("/")
    public String adminHomePage() {
        return "admin/index";
    }
}
