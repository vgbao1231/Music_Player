package org.music_player.web.service;

import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Genre;
import org.music_player.web.entity.Song;
import org.music_player.web.entity.SongAlbum;
import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongPlaylistRepository songPlaylistRepository;
    @Autowired
    private SongAlbumRepository songAlbumRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public SongDTO convertSongEntityToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setSongId(song.getSongId());
        songDTO.setSongName(song.getSongName());
        songDTO.setGenre(song.getGenre());
        songDTO.setArtist(song.getArtist());
        songDTO.setAudio(song.getSongAudio());
        songDTO.setSongImg(song.getSongImg());
        return songDTO;
    }

    public List<SongDTO> listAllSong() {
        List<SongDTO> listAllSong = new ArrayList<>();
        for (Song song : songRepository.findAllSong()) {
            listAllSong.add(convertSongEntityToDTO(song));
        }
        return listAllSong;
    }

    public void addSong(Song song, MultipartFile songImg, MultipartFile songAudio) throws IOException {
        //Kiểm tra nhạc
        if (songAudio == null || songAudio.isEmpty())
            throw new IOException("Tệp bài hát không được để trống");
        // Đọc dữ liệu audio từ MultipartFile
        if (songAudio.getOriginalFilename() == null ||
            !songAudio.getOriginalFilename().toLowerCase().endsWith(".mp3"))
            throw new IOException("Tệp vừa chọn không phải là tệp âm thanh");

        //Kiểm tra ảnh
        if (songImg == null || songImg.isEmpty())
            throw new IOException("Ảnh bài hát không được để trống");
        // Đọc dữ liệu hình ảnh từ MultipartFile
        if (ImageIO.read(songImg.getInputStream()) == null)
            throw new IOException("Tệp vừa chọn không phải là hình ảnh");

        // Lấy đường dẫn để thêm nhạc vào
        String audioUploadDir = "/assets/song/" + songAudio.getOriginalFilename();
        song.setSongAudio(audioUploadDir);

        // Xử lý trường hợp nhạc trùng lặp
        if (audioIsExisted(song.getSongAudio())) {
            throw new IOException("Đã có bài hát có tệp nhạc này");
        }

        // Lấy đường dẫn để thêm ảnh vào
        String imgUploadDir = "/assets/img/song/" + songImg.getOriginalFilename();
        song.setSongImg(imgUploadDir);

        // Xử lý trường hợp ảnh trùng lặp
        if (imgIsExisted(song.getSongImg())) {
            throw new IOException("Đã có bài hát có hình ảnh này");
        }

        Path imgPath = Paths.get("./src/main/resources/static" + imgUploadDir);
        Files.write(imgPath, songImg.getBytes());
        Path audioPath = Paths.get("./src/main/resources/static" + audioUploadDir);
        Files.write(audioPath, songAudio.getBytes());
        songRepository.save(song);
    }

    public void updateSong(Song song, MultipartFile songImg) throws IOException{
        Song s = songRepository.findBySongId(song.getSongId());
        song.setSongAudio(s.getSongAudio());
        song.setSongImg(s.getSongImg());

        // Nếu có ảnh mới thì sửa ko thì thôi
        if (!songImg.isEmpty()) {
            // Đọc dữ liệu hình ảnh từ MultipartFile
            if (ImageIO.read(songImg.getInputStream()) == null)
                throw new IOException("Tệp vừa chọn không phải là hình ảnh");
            String imgUploadDir = "/assets/img/song/" + songImg.getOriginalFilename();
            Path imgPath = Paths.get("./src/main/resources/static" + imgUploadDir);
            if (imgIsExisted(imgUploadDir)) throw new IOException("Đã có bài hát có hình ảnh này");
            //Xóa ảnh cũ
            deleteFile("./src/main/resources/static" + song.getSongImg());
            Files.write(imgPath, songImg.getBytes());
            song.setSongImg(imgUploadDir);
        }
        songRepository.save(song);
    }

    public void deleteSong(Song song) {
        Song s = songRepository.getReferenceById(song.getSongId());
        deleteFile("./src/main/resources/static" + song.getSongImg());
        deleteFile("./src/main/resources/static" + song.getSongAudio());
        songRepository.delete(s);
    }

    public List<SongDTO> listAllSongByPlaylist(Integer playlistId) {
        List<SongDTO> listAllSongByPlaylist = new ArrayList<>();
        for (SongPlaylist songPlaylist : songPlaylistRepository.listAllSongByPlaylist(playlistId)) {
            listAllSongByPlaylist.add(convertSongEntityToDTO
                (songRepository.findBySongId(songPlaylist.getSong().getSongId())));
        }
        return listAllSongByPlaylist;
    }

    public List<SongDTO> listAllSongByAlbum(Integer albumId) {
        List<SongDTO> listAllSongByAlbum = new ArrayList<>();
        for (SongAlbum songAlbum : songAlbumRepository.findAllSongByAlbum(albumId)) {
            listAllSongByAlbum.add(convertSongEntityToDTO
                (songRepository.findBySongId(songAlbum.getSong().getSongId())));
        }
        return listAllSongByAlbum;
    }

    public List<SongDTO> listAllSongByGenre(Integer genreId) {
        List<SongDTO> listAllSongByGenre = new ArrayList<>();
        for (Song song : songRepository.findAllSongByGenreId(genreId)) {
            listAllSongByGenre.add(convertSongEntityToDTO(song));
        }
        return listAllSongByGenre;
    }

    public void addSongToPlaylist(Integer songId, Integer playlistId) throws IOException {
        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setSong(songRepository.findBySongId(songId));
        songPlaylist.setPlaylist(playlistRepository.findByPlaylistId(playlistId));
        try {
            songPlaylistRepository.save(songPlaylist);
        } catch (Exception e) {
            throw new IOException("Bài hát đã có trong playlist");
        }
    }

    public boolean imgIsExisted(String img) {
        return songRepository.existsBySongImg(img);
    }

    public boolean audioIsExisted(String audio) {
        return songRepository.existsBySongAudio(audio);
    }

    public Song findBySongId(Integer songId) {
        return songRepository.findBySongId(songId);
    }

    public void saveSong(Song song) {
        songRepository.save(song);
    }

    public void deleteSong(Integer songId) {
        Song song = songRepository.getReferenceById(songId);
        deleteFile("./src/main/resources/static" + song.getSongImg());
        deleteFile("./src/main/resources/static" + song.getSongAudio());
        songRepository.deleteById(songId);
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Xóa file thành công: " + filePath);
            } else {
                System.out.println("Không thể xóa file: " + filePath);
            }
        } else {
            System.out.println("File không tồn tại: " + filePath);
        }
    }
}
