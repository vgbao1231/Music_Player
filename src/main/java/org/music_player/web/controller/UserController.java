package org.music_player.web.controller;

import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.dto.GenreDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private GenreService genreService;
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
    @ModelAttribute("listAllSong")
    public List<SongDTO> listAllSong() {
        return songService.listAllSong();
    }

    @RequestMapping(value = {"", "/home"}, method = RequestMethod.GET)
    public String userHome(Model model) {
        List<AlbumDTO> listAllAlbum = albumService.listAllAlbum();
        model.addAttribute("listAllAlbum", listAllAlbum);
        return "user/home";
    }
    @RequestMapping("/{topic}/{topicId}/songIndex={songIndex}")
    public String userSongTopic(Model model,
                                @PathVariable String topic,
                                @PathVariable Integer topicId,
                                @PathVariable Integer songIndex) {
        List<SongDTO> listAllSong = switch (topic) {
            case "genre" -> songService.listAllSongByGenre(topicId);
            case "album" -> songService.listAllSongByAlbum(topicId);
            default -> songService.listAllSong();
        };
        model.addAttribute("currentSong", songIndex);
        return "user/playlist";
    }
    @RequestMapping("/topic/album/{topicId}")
    public String userAlbum(Model model, @PathVariable Integer topicId) {
        List<SongDTO> listAllSongByAlbum = songService.listAllSongByAlbum(topicId);
        AlbumDTO album = albumService.convertAlbumEntityToDTO(albumService.findAlbumByAlbumId(topicId));
        model.addAttribute("listAllSong", listAllSongByAlbum);
        model.addAttribute("topic", album);
        model.addAttribute("topicName", "album");
        return "user/topic-detail";
    }
    @RequestMapping("/topic/genre/{topicId}")
    public String userGenre(Model model, @PathVariable Integer topicId) {
        List<SongDTO> listAllSongByGenre = songService.listAllSongByGenre(topicId);
        GenreDTO genre = genreService.convertGenreEntityToDTO(genreService.findGenreByGenreId(topicId));
        model.addAttribute("listAllSong", listAllSongByGenre);
        model.addAttribute("topic", genre);
        model.addAttribute("topicName", "genre");
        return "user/topic-detail";
    }

    @RequestMapping("/topic")
    public String userGenres(Model model) {
        List<AlbumDTO> listAllAlbum = albumService.listAllAlbum();
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllAlbum", listAllAlbum);
        model.addAttribute("listAllGenre", listAllGenre);
        return "user/topic";
    }

    @RequestMapping("/playlist/{playlistId}")
    public String userPlaylist(Model model, @PathVariable Integer playlistId) {
        List<SongDTO> listAllSongByPlaylist = songService.listAllSongByPlaylist(playlistId);
        model.addAttribute("listAllSong", listAllSongByPlaylist);
        model.addAttribute("currentSong", 0);
        model.addAttribute("topicName", "playlist");
        return "user/playlist";
    }

    @RequestMapping("/album/{albumId}/songIndex={songIndex}")
    public String userAllSongAlbum(Model model,
                                   @PathVariable Integer albumId,
                                   @PathVariable Integer songIndex) {
        List<SongDTO> listAllSongByAlbum = songService.listAllSongByAlbum(albumId);
        model.addAttribute("listAllSong", listAllSongByAlbum);
        model.addAttribute("currentSong", songIndex);
        return "user/playlist";
    }

    @PostMapping("/addPlaylist")
    public String addPlaylist(@RequestParam("playlist_name") String playlistName,
                              @ModelAttribute("userId") Integer userId,
                              RedirectAttributes redirectAttributes) {
        try {
            Playlist playlist = new Playlist();
            playlist.setPlaylistName(playlistName);
            playlist.setUser(userService.findByUserId(userId));
            playlistService.savePlaylist(playlist);
            redirectAttributes.addFlashAttribute("success","Tạo playlist thành công");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/user/home";
    }

    @PostMapping("/addSongToPlaylist")
    public String addSongToPlaylist(@RequestParam("songId") Integer songId,
                                    @RequestParam("playlistId") Integer playlistId,
                                    @ModelAttribute("userId") Integer userId,
                                    RedirectAttributes redirectAttributes) {
        try {
            songService.addSongToPlaylist(songId, playlistId);
            redirectAttributes.addFlashAttribute("success","Thêm vào playlist thành công");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/user/home";
    }

    @PostMapping("/updatePlaylist")
    public String updatePlaylist(@RequestParam("playlistId") Integer playlistId,
                                 @RequestParam("playlist_name") String playlistName,
                                 RedirectAttributes redirectAttributes) {
        try {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            playlist.setPlaylistName(playlistName);
            playlistService.savePlaylist(playlist);
            redirectAttributes.addFlashAttribute("success","Cập nhật playlist thành công");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/user/home";
    }

    @RequestMapping("/playlist/deletePlaylistId={playlistId}")
    public String deletePlaylist(@PathVariable("playlistId") Integer playlistId,
                                 RedirectAttributes redirectAttributes) {
        try {
            playlistService.deletePlaylist(playlistId);
            redirectAttributes.addFlashAttribute("success","Xóa playlist thành công");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/user/home";
    }
    @RequestMapping("/playlist/{playlistId}/deleteSongId={songId}")
    public String deleteSong(@PathVariable("playlistId") Integer playlistId,
                             @PathVariable("songId") Integer songId,
                             RedirectAttributes redirectAttributes) {
        try {
            songPlaylistService.deleteSongFromPlaylist(playlistId,songId);
            redirectAttributes.addFlashAttribute("success","Xóa bài hát khỏi playlist thành công");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/user/playlist/{playlistId}";
    }
}
