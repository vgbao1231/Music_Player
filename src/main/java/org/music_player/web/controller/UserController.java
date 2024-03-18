package org.music_player.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.music_player.web.dto.PlaylistDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.Playlist;
import org.music_player.web.entity.User;
import org.music_player.web.service.PlaylistService;
import org.music_player.web.service.SongService;
import org.music_player.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private SongService songService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;

    @ModelAttribute("listAllPlaylist")
    public List<PlaylistDTO> listAllPlaylist(HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        return playlistService.listALlPlaylist(userId);
    }
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String getLoginView() {
        return "login";
    }

    @RequestMapping(value = {"/user", "/user/home"}, method = RequestMethod.GET)
    public String userHomePage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong", listAllSong);
        return "user/home";
    }

    @RequestMapping("user/genres")
    public String userGenresPage() {
        return "user/genres";
    }

    @PostMapping("user/addPlaylist")
    public String addPlaylist(@RequestParam("title") String title, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setTitle(title);
        playlistDTO.setUser(userService.findByUserId(userId));
        Playlist playlist = playlistService.convertPlaylistDTOToEntity(playlistDTO);
        playlistService.savePlaylist(playlist);
        return "redirect:/user/home";
    }


    @RequestMapping(value = {"/user/playlist"}, method = RequestMethod.GET)
    public String userPlaylistPage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong", listAllSong);
        return "user/playlist";
    }
}
