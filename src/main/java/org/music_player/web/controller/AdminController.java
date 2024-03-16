package org.music_player.web.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
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
    public String adminHomePage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        model.addAttribute("listAllSong", listAllSong);
        return "admin/song";
    }
    @PostMapping("addSong")
    public String addNewSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("genre") Integer genre,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("img") MultipartFile imageFile
    ) throws IOException {
        SongDTO songDTO = new SongDTO();
        songDTO.setTitle(title);
        songDTO.setArtist(artist);
        songDTO.setGenre(genreService.findGenreByGenreId(genre));
        songDTO.setAudio(songService.encodingFileToString(audio));
        songDTO.setSongImg(songService.encodingFileToString(imageFile));
        Song song = songService.convertSongDTOToEntity(songDTO);
        songService.saveSong(song);
        return "redirect:/admin/song";
    }
}
