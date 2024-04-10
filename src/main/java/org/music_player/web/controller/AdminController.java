package org.music_player.web.controller;

import jakarta.transaction.Transactional;
import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.dto.GenreDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Song;
import org.music_player.web.service.AlbumService;
import org.music_player.web.service.GenreService;
import org.music_player.web.service.SongAlbumService;
import org.music_player.web.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // Quản lý thao tác bên admin
    @RequestMapping("/song")
    public String adminSongPage(Model model) {
        List<SongDTO> listAllSong = songService.listALlSong();
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        model.addAttribute("listAllSong", listAllSong);
        return "admin/song";
    }

    @RequestMapping("/album")
    public String adminAlbumPage(Model model) {
        List<AlbumDTO> listAllAlbum = albumService.listAllAlbum();
        model.addAttribute("listAllAlbum", listAllAlbum);
        return "admin/album";
    }

    @RequestMapping("/album/{albumId}")

    public String adminAlbumDetailPage(Model model, @PathVariable Integer albumId) {
        List<SongDTO> listAllSong = songService.listALlSong();
        List<SongDTO> listAllSongByAlbum = songService.listAllSongByAlbum(albumId);
        AlbumDTO album = albumService.convertAlbumEntityToDTO(albumService.findByAlbumId(albumId));
        model.addAttribute("listAllSong", listAllSong);
        model.addAttribute("listAllSongAlbum", listAllSongByAlbum);
        model.addAttribute("album", album);
        return "admin/album-detail";
    }

    @PostMapping("/album/addSongToAlbum")
    public String addSongToPlaylist(@RequestParam("songId") Integer songId,
                                    @RequestParam("albumId") Integer albumId) {
        songService.addSongToAlbum(songId, albumId);
        return "redirect:/admin/album/" + albumId;
    }

    @PostMapping("/addSong")
    public String addSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("genre") Integer genre,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("img") MultipartFile img
    ) throws IOException {
        // Lấy đường dẫn để thêm nhạc và ảnh vào
        String audioUploadDir = "./src/main/resources/static/assets/song/" + audio.getOriginalFilename();
        Path audioPath = Paths.get(audioUploadDir);

        String imgUploadDir = "./src/main/resources/static/assets/img/song/" + img.getOriginalFilename();
        Path imgPath = Paths.get(imgUploadDir);

        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setGenre(genreService.findGenreByGenreId(genre));
        song.setAudio(audioUploadDir.replace("./src/main/resources/static", ""));
        song.setSongImg(imgUploadDir.replace("./src/main/resources/static", ""));

        //Kiểm tra ảnh và nhạc đã tồn tại chưa
        boolean isDuplicateImage = songService.imgIsExisted(song.getSongImg());
        boolean isDuplicateAudio = songService.audioIsExisted(song.getAudio());
        // Xử lý trường hợp đã tồn tại
        if (isDuplicateImage || isDuplicateAudio) {
            System.out.println("Trùng lặp");
        } else {
            Files.write(audioPath, audio.getBytes());
            Files.write(imgPath, img.getBytes());

            songService.saveSong(song);
        }
        return "redirect:/admin/song";
    }

    @PostMapping("/addAlbum")
    public String addSong(
            @RequestParam("title") String title,
            @RequestParam("img") MultipartFile img
    ) throws IOException {
        // Lấy đường dẫn để thêm nhạc và ảnh vào
        String imgUploadDir = "./src/main/resources/static/assets/img/album/" + img.getOriginalFilename();
        Path imgPath = Paths.get(imgUploadDir);
        Files.write(imgPath, img.getBytes());

        Album album = new Album();
        album.setAlbumName(title);
        album.setAlbumImg(imgUploadDir.replace("./src/main/resources/static", ""));

        boolean isDuplicateImage = albumService.imgIsExisted(album.getAlbumImg());

        // Xử lý trường hợp trùng lặp
        if (isDuplicateImage) {
            System.out.println("Trùng lặp");
        } else {
            albumService.saveAlbum(album);
        }
        return "redirect:/admin/album";
    }

    @PostMapping("/updateSong")
    public String updateSong(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("genre") Integer genre,
            @RequestParam("img") MultipartFile img) throws IOException {
        Song song = songService.getSongById(id);
        song.setTitle(title);
        song.setArtist(artist);
        song.setGenre(genreService.findGenreByGenreId(genre));
        // Nếu có ảnh mới thì sửa ko thì thôi
        boolean isDuplicateImage =
                songService.imgIsExisted("/assets/img/song/" + img.getOriginalFilename());
        if (!img.isEmpty() && !isDuplicateImage) {
            //Xóa ảnh cũ
            songService.deleteFile("./src/main/resources/static" + song.getSongImg());
            String imgUploadDir = "./src/main/resources/static/assets/img/song/" + img.getOriginalFilename();
            Path imgPath = Paths.get(imgUploadDir);
            Files.write(imgPath, img.getBytes());
            song.setSongImg(imgUploadDir.replace("./src/main/resources/static", ""));
        } else {
            System.out.println("Trống hoặc trùng lặp");
        }
        songService.saveSong(song);
        return "redirect:/admin/song";
    }

    @PostMapping(value = "/deleteId={songId}")
    public String deleteSong(@PathVariable Integer songId) {
        songService.deleteSong(songId);
        return "redirect:/admin/song";
    }

    @RequestMapping("/album/{albumId}/deleteSongId={songId}")
    public String deleteSong(@PathVariable("albumId") Integer albumId,
                             @PathVariable("songId") Integer songId) {
        songAlbumService.deleteSongFromAlbum(albumId,songId);
        return "redirect:/admin/album/{albumId}";
    }
}
