package org.music_player.web.controller;

import org.music_player.web.dto.PlaylistDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.Playlist;
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

    @ModelAttribute("userId")
    public Integer getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getUser().getUserId();
            }
        }
        return null;
    }
    @ModelAttribute("listAllPlaylist")
    public List<PlaylistDTO> listAllPlaylist(@ModelAttribute("userId") Integer userId){
        return playlistService.listALlPlaylist(userId);
    }
    @RequestMapping(value = {"/user", "/user/home"}, method = RequestMethod.GET)
    public String userHome(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong", listAllSong);
        return "user/home";
    }

    @RequestMapping("user/genres")
    public String userGenres() {
        return "user/genres";
    }

    @PostMapping("user/addPlaylist")
    public String addPlaylist(@RequestParam("title") String title, @ModelAttribute("userId") Integer userId) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setTitle(title);
        playlistDTO.setUser(userService.findByUserId(userId));
        Playlist playlist = playlistService.convertPlaylistDTOToEntity(playlistDTO);
        playlistService.savePlaylist(playlist);
        return "redirect:/user/home";
    }
    @PostMapping("user/addSongToPlaylist")
    public String addSongToPlaylist(@RequestParam("songId") Integer songId,
                                    @RequestParam("playlistId") Integer playlistId,
                                    @ModelAttribute("userId") Integer userId) {
        System.out.println(songId);
        System.out.println(playlistId);
        songService.addSongToPlaylist(songId,playlistId);
        return "redirect:/user/home";
    }

    @RequestMapping("/user/playlist/{playlistId}")
    public String userPlaylist(Model model, @PathVariable Integer playlistId) {
        List<SongDTO> listAllSongByPlaylist = songService.listAllSongByPlaylist(playlistId);
        model.addAttribute("listAllSong", listAllSongByPlaylist);
        model.addAttribute("currentSong", 0);
        return "user/playlist";
    }
    @RequestMapping("/user/playlist/songIndex={songIndex}")
    public String userAllSong(Model model, @PathVariable Integer songIndex) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("currentSong", songIndex);
        return "user/playlist";
    }
}
