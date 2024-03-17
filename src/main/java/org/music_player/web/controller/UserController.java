package org.music_player.web.controller;

import org.music_player.web.dto.SongDTO;
import org.music_player.web.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private SongService songService;
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String getLoginView() {
        return "login";
    }

    @RequestMapping(value = {"/user", "/user/home"}, method = RequestMethod.GET)
    public String userHomePage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong",listAllSong);
        return "user/home";
    }

    @RequestMapping("user/genres")
    public String userGenresPage() {
        return "user/genres";
    }

    @RequestMapping(value = {"/user/playlist"}, method = RequestMethod.GET)
    public String userPlaylistPage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong",listAllSong);
        return "user/playlist";
    }
}
