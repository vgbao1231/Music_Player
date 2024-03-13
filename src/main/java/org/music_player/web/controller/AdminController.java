package org.music_player.web.controller;

import org.music_player.web.entity.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = {"","/song"})
    public String adminHomePage(Model model) {
        CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", user.getUsername());
        return "admin/song";
    }
}
