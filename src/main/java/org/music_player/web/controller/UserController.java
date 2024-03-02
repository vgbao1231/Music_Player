package org.music_player.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping()
    public String adminPage() {
        return "redirect:/user/home";
    }
    @RequestMapping("login")
    public String getLoginView() {
        return "login";
    }
    @RequestMapping("user/home")
    public String getHomeView() {
        return "user/home";
    }
    @RequestMapping("user/genres")
    public String getGenresView() {
        return "user/genres";
    }
    @RequestMapping("user/playlist")
    public String getPlaylistView() {
        return "user/playlist";
    }

}
