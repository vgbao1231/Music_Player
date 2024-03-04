package org.music_player.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String getLoginView() {
        return "login";
    }

    @RequestMapping(value = {"/user", "/user/home"}, method = RequestMethod.GET)
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
