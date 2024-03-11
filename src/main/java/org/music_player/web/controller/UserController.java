package org.music_player.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

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

    @ModelAttribute("items")
    public List<String> getItems() {
        // Thay thế phần này bằng logic lấy danh sách của bạn từ nguồn dữ liệu
        List<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        return items;
    }
}
