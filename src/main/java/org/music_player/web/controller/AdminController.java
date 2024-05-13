package org.music_player.web.controller;

import jakarta.validation.ConstraintViolationException;
import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.dto.GenreDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Genre;
import org.music_player.web.entity.Song;
import org.music_player.web.service.AlbumService;
import org.music_player.web.service.GenreService;
import org.music_player.web.service.SongAlbumService;
import org.music_player.web.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private SongAlbumService songAlbumService;

    @RequestMapping()
    public String redirectAdminPage() {
        return "redirect:admin/song";
    }

    // Quản lý bài hát (Song)
    @RequestMapping("/song")
    public String adminSongPage(Model model) {
        List<SongDTO> listAllSong = songService.listAllSong();
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("song", new Song());
        return "admin/song";
    }

    @PostMapping("/song/addSong")
    public String addSong(@ModelAttribute Song song,
                          @RequestParam("img") MultipartFile img,
                          @RequestParam("audio") MultipartFile audio,
                          RedirectAttributes redirectAttributes) {
        try {
            songService.addSong(song, img, audio);
            redirectAttributes.addFlashAttribute("success", "Thêm bài hát thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/song";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/song";
        }
        return "redirect:/admin/song";
    }

    @PostMapping("/song/updateSong")
    public String updateSong(@ModelAttribute Song song,
                             @RequestParam("img") MultipartFile img,
                             RedirectAttributes redirectAttributes) {
        try {
            songService.updateSong(song, img);
            redirectAttributes.addFlashAttribute("success", "Cập nhật bài hát thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/song";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/song";
        }
        return "redirect:/admin/song";
    }

    @RequestMapping("/song/deleteSong")
    public String deleteSong(@ModelAttribute Song song,
                              RedirectAttributes redirectAttributes) {
        try {
            songService.deleteSong(song);
            redirectAttributes.addFlashAttribute("success", "Xóa bài hát thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/song";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/song";
        }
        return "redirect:/admin/song";
    }

    // Quản lý thể loại
    @RequestMapping("/genre")
    public String adminGenresPage(Model model) {
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        model.addAttribute("genre", new Genre());
        return "admin/genre";
    }

    @PostMapping("/genre/addGenre")
    public String addGenre(@ModelAttribute Genre genre,
                           @RequestParam("img") MultipartFile img,
                           RedirectAttributes redirectAttributes) {
        try {
            genreService.addGenre(genre, img);
            redirectAttributes.addFlashAttribute("success", "Thêm thể loại thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/genre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/genre";
        }
        return "redirect:/admin/genre";
    }

    @PostMapping("/genre/updateGenre")
    public String updateGenre(@ModelAttribute Genre genre,
                              @RequestParam("img") MultipartFile img,
                              RedirectAttributes redirectAttributes) {
        try {
            genreService.updateGenre(genre, img);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thể loại thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/genre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/genre";
        }
        return "redirect:/admin/genre";
    }

    @PostMapping("/genre/deleteGenre")
    public String deleteGenre(@ModelAttribute Genre genre,
                              RedirectAttributes redirectAttributes) {
        try {
            genreService.deleteGenre(genre);
            redirectAttributes.addFlashAttribute("success", "Xóa thể loại thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/genre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/genre";
        }
        return "redirect:/admin/genre";
    }

    // Quản lý album
    @RequestMapping("/album")
    public String adminAlbumPage(Model model) {
        List<AlbumDTO> listAllAlbum = albumService.listAllAlbum();
        model.addAttribute("listAllAlbum", listAllAlbum);
        model.addAttribute("album", new Album());
        return "admin/album";
    }

    @PostMapping("/album/addAlbum")
    public String addAlbum(@ModelAttribute Album album,
                           @RequestParam("img") MultipartFile img,
                           RedirectAttributes redirectAttributes) {
        try {
            albumService.addAlbum(album, img);
            redirectAttributes.addFlashAttribute("success", "Thêm album thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/album";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/album";
        }
        return "redirect:/admin/album";
    }

    @PostMapping("/album/updateAlbum")
    public String updateSong(@ModelAttribute Album album,
                             RedirectAttributes redirectAttributes) {
        try {
            albumService.updateAlbum(album);
            redirectAttributes.addFlashAttribute("success", "Cập nhật album thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/album/" + album.getAlbumId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/album/" + album.getAlbumId();
        }
        return "redirect:/admin/album/" + album.getAlbumId();
    }

    @PostMapping("/album/deleteAlbum")
    public String deleteAlbum(@ModelAttribute Album album,
                              RedirectAttributes redirectAttributes) {
        try {
            albumService.deleteAlbum(album);
            redirectAttributes.addFlashAttribute("success", "Xóa album thành công");
        } catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/album";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/album";
        }
        return "redirect:/admin/album";
    }

    @RequestMapping("/album/{albumId}")
    public String adminAlbumDetailPage(Model model, @PathVariable Integer albumId) {
        List<SongDTO> listAllSong = songService.listAllSong();
        List<SongDTO> listAllSongByAlbum = songService.listAllSongByAlbum(albumId);
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("listAllSongAlbum", listAllSongByAlbum);
        Album album = albumService.findAlbumByAlbumId(albumId);
        model.addAttribute("album", album);
        return "admin/album-detail";
    }

    @PostMapping("/album/addSongAlbum")
    public String addSongToPlaylist(@RequestParam("songId") Integer songId,
                                    @ModelAttribute Album album,
                                    RedirectAttributes redirectAttributes) {
        try {
            songAlbumService.addSongAlbum(songId, album);
            redirectAttributes.addFlashAttribute("success", "Thêm bài hát vào album thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Bài hát đã có trong album");
            return "redirect:/admin/album/" + album.getAlbumId();
        }
        return "redirect:/admin/album/" + album.getAlbumId();
    }

    @PostMapping("/album/deleteSongAlbum")
    public String deleteSongFromAlbum(@RequestParam("songId") Integer songId,
                                      @ModelAttribute Album album,
                                      RedirectAttributes redirectAttributes) {
        try {
            songAlbumService.deleteSongFromAlbum(songId, album);
            redirectAttributes.addFlashAttribute("success", "Xóa bài hát khỏi album thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa bài hát khỏi album");
            return "redirect:/admin/album/" + album.getAlbumId();
        }
        return "redirect:/admin/album/" + album.getAlbumId();
    }
}
