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

    @PostMapping("/addSong")
    public String addSong(
        @RequestParam("song_name") String songName,
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
        song.setSongName(songName);
        song.setArtist(artist);
        song.setGenre(genreService.findByGenreId(genre));
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

    @PostMapping("/updateSong")
    public String updateSong(
        @RequestParam("id") Integer id,
        @RequestParam("song_name") String songName,
        @RequestParam("artist") String artist,
        @RequestParam("genre") Integer genre,
        @RequestParam("img") MultipartFile img) throws IOException {
        Song song = songService.findBySongId(id);
        song.setSongName(songName);
        song.setArtist(artist);
        song.setGenre(genreService.findByGenreId(genre));
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

    @RequestMapping("/song/deleteSongId={songId}")
    public String deleteSong(@PathVariable Integer songId) {
        songService.deleteSong(songId);
        return "redirect:/admin/song";
    }

    // Quản lý thể loại
    @RequestMapping("/genre")
    public String adminGenresPage(Model model) {
        List<GenreDTO> listAllGenre = genreService.listALlGenre();
        model.addAttribute("listAllGenre", listAllGenre);
        return "admin/genre";
    }

    @PostMapping("/genre/addGenre")
    public String addGenre(
        @RequestParam("genre_name") String genreName,
        @RequestParam("img") MultipartFile img
    ) throws IOException {
        // Lấy đường dẫn để thêm nhạc và ảnh vào
        String imgUploadDir = "./src/main/resources/static/assets/img/genre/" + img.getOriginalFilename();
        Path imgPath = Paths.get(imgUploadDir);
        Files.write(imgPath, img.getBytes());

        Genre genre = new Genre();
        genre.setGenreName(genreName);
        genre.setGenreImg(imgUploadDir.replace("./src/main/resources/static", ""));

        boolean isDuplicateImage = genreService.imgIsExisted(genre.getGenreImg());

        // Xử lý trường hợp trùng lặp
        if (isDuplicateImage) {
            System.out.println("Trùng lặp");
        } else {
            genreService.saveGenre(genre);
        }
        return "redirect:/admin/genre";
    }

    @PostMapping("/genre/updateGenre")
    public String updateGenre(
        @RequestParam("genre_id") Integer genreId,
        @RequestParam("genre_name") String genreName,
        @RequestParam("img") MultipartFile img) throws IOException {
        Genre genre = genreService.findByGenreId(genreId);
        genre.setGenreName(genreName);
        // Nếu có ảnh mới thì sửa ko thì thôi
        boolean isDuplicateImage =
            genreService.imgIsExisted("/assets/img/genre/" + img.getOriginalFilename());
        if (!img.isEmpty() && !isDuplicateImage) {
            //Xóa ảnh cũ
            genreService.deleteFile("./src/main/resources/static" + genre.getGenreImg());
            String imgUploadDir = "./src/main/resources/static/assets/img/genre/" + img.getOriginalFilename();
            Path imgPath = Paths.get(imgUploadDir);
            Files.write(imgPath, img.getBytes());
            genre.setGenreImg(imgUploadDir.replace("./src/main/resources/static", ""));
        } else {
            System.out.println("Trống hoặc trùng lặp");
        }
        genreService.saveGenre(genre);
        return "redirect:/admin/genre";
    }

    @RequestMapping("/genre/deleteGenreId={genreId}")
    public String deleteGenre(@PathVariable("genreId") Integer genreId) {
        genreService.deleteGenre(genreId);
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

    @RequestMapping("/album/{albumId}")
    public String adminAlbumDetailPage(Model model, @PathVariable Integer albumId) {
        List<SongDTO> listAllSong = songService.listAllSong();
        List<SongDTO> listAllSongByAlbum = songService.listAllSongByAlbum(albumId);
        Album album = albumService.findByAlbumId(albumId);
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

    @PostMapping("/album/addAlbum")
    public String addAlbum(
        @ModelAttribute Album album,
        @RequestParam("img") MultipartFile img,
        RedirectAttributes redirectAttributes
    ) {
        try {
            albumService.addAlbum(album,img);
            redirectAttributes.addFlashAttribute("success", "Thêm album thành công");
        }catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/album";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/album";
        }
        return "redirect:/admin/album";
    }

    @PostMapping("/album/{albumId}/updateAlbum")
    public String updateSong(
        @PathVariable("albumId") Integer albumId,
        @ModelAttribute Album album,
        RedirectAttributes redirectAttributes) {
        try {
            album.setAlbumId(albumId);
            albumService.updateAlbum(album);
            redirectAttributes.addFlashAttribute("success", "Cập nhật album thành công");
        }catch (ConstraintViolationException ex) {
            String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/album/{albumId}";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/album/{albumId}";
        }
        return "redirect:/admin/album/{albumId}";
    }

    @RequestMapping("/album/{albumId}/deleteSongId={songId}")
    public String deleteSongFromAlbum(@PathVariable("albumId") Integer albumId,
                                      @PathVariable("songId") Integer songId) {
        songAlbumService.deleteSongFromAlbum(albumId, songId);
        return "redirect:/admin/album/{albumId}";
    }

    @RequestMapping("/album/deleteAlbumId={albumId}")
    public String deleteAlbum(@PathVariable("albumId") Integer albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/admin/album";
    }
}
