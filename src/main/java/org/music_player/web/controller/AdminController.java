package org.music_player.web.controller;

import jakarta.transaction.Transactional;
import org.music_player.web.dto.GenreDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Genre;
import org.music_player.web.entity.Song;
import org.music_player.web.service.GenreService;
import org.music_player.web.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private SongService songService;
    @Autowired
    private GenreService genreService;
    @RequestMapping()
    public String redirectAdminPage(){
        return "redirect:admin/song";
    }

    // Quản lý thao tác bên admin
    @RequestMapping(value = "/song", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String adminSongPage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        model.addAttribute("listAllSong", listAllSong);
        return "admin/song";
    }
    @PostMapping("addSong")
    public String addSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("genre") Integer genre,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("img") MultipartFile img
    ) throws IOException {
        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setGenre(genreService.findGenreByGenreId(genre));
        song.setAudio(songService.encodingFileToString(audio));
        song.setSongImg(songService.encodingFileToString(img));
        songService.saveSong(song);
        return "redirect:/admin/song";
    }
    @PostMapping("updateSong")
    public String updateSong(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("genre") Integer genre,
            @RequestParam("img") MultipartFile img
    ) throws IOException {
        Song song = songService.getSongById(id);
        song.setTitle(title);
        song.setArtist(artist);
        song.setGenre(genreService.findGenreByGenreId(genre));
        // Nếu có ảnh mới thì sửa ko thì thôi
        if(!img.isEmpty()){
            song.setSongImg(songService.encodingFileToString(img));
        }
        songService.saveSong(song);
        return "redirect:/admin/song";
    }
    @PostMapping(value = "/deleteId={songId}")
    public String deleteSong(@PathVariable Integer songId){
        songService.deleteSong(songId);
        System.out.println("vvv"+songId);
        return "redirect:/admin/song";
    }
}
