package org.music_player.web.controller;

import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.dto.PlaylistDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.Playlist;
import org.music_player.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private SongPlaylistService songPlaylistService;

    @ModelAttribute("userId")
    public Integer getUserId() {
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
    public List<PlaylistDTO> listAllPlaylist(@ModelAttribute("userId") Integer userId) {
        return playlistService.listALlPlaylist(userId);
    }

    @RequestMapping(value = {"", "/home"}, method = RequestMethod.GET)
    public String userHome(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        List<AlbumDTO> listAllAlbum = albumService.listAllAlbum();
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("listAllAlbum", listAllAlbum);
        return "user/home";
    }

    @RequestMapping("/genres")
    public String userGenres() {
        return "user/genres";
    }

    @RequestMapping("/playlist/{playlistId}")
    public String userPlaylist(Model model, @PathVariable Integer playlistId) {
        List<SongDTO> listAllSongByPlaylist = songService.listAllSongByPlaylist(playlistId);
        model.addAttribute("listAllSong", listAllSongByPlaylist);
        model.addAttribute("currentSong", 0);
        return "user/playlist";
    }

    @RequestMapping("/playlist/songIndex={songIndex}")
    public String userAllSong(Model model, @PathVariable Integer songIndex) {
        List<SongDTO> listAllSong = songService.listALlSong();
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("currentSong", songIndex);
        return "user/playlist";
    }

    @PostMapping("/addPlaylist")
    public String addPlaylist(@RequestParam("title") String title, @ModelAttribute("userId") Integer userId) {
        Playlist playlist = new Playlist();
        playlist.setTitle(title);
        playlist.setUser(userService.findByUserId(userId));
        playlistService.savePlaylist(playlist);
        return "redirect:/user/home";
    }

    @PostMapping("/addSongToPlaylist")
    public String addSongToPlaylist(@RequestParam("songId") Integer songId,
                                    @RequestParam("playlistId") Integer playlistId,
                                    @ModelAttribute("userId") Integer userId) {
        songService.addSongToPlaylist(songId, playlistId);
        return "redirect:/user/home";
    }

    @PostMapping("/updatePlaylist")
    public String updatePlaylist(@RequestParam("playlistId") Integer playlistId,
                                 @RequestParam("title") String title) throws IOException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        playlist.setTitle(title);
        playlistService.savePlaylist(playlist);
        return "redirect:/user/home";
    }

    @RequestMapping("/playlist/deletePlaylistId={playlistId}")
    public String deletePlaylist(@PathVariable("playlistId") Integer playlistId) {
        playlistService.deletePlaylist(playlistId);
        return "redirect:/user/home";
    }
    @RequestMapping("/playlist/{playlistId}/deleteSongId={songId}")
    public String deleteSong(@PathVariable("playlistId") Integer playlistId,
                             @PathVariable("songId") Integer songId) {
        songPlaylistService.deleteSongFromPlaylist(playlistId,songId);
        return "redirect:/user/playlist/{playlistId}";
    }

}
