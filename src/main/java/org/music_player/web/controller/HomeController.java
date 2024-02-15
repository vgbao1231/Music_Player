package org.music_player.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping()
    public String main() {
        return "main";
    }
    @RequestMapping("home")
    public String getHomeView() {
        return "home";
    }
    @RequestMapping("login")
    public String getLoginView() {
        return "login";
    }
    @RequestMapping("account")
    public String getAccountView() {
        return "account";
    }
    @RequestMapping("playlist")
    public String getPlaylistView() {
        return "playlist";
    }

}
