package org.music_player.web.service;

import jakarta.transaction.Transactional;
import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.SongAlbum;
import org.music_player.web.repository.AlbumRepository;
import org.music_player.web.repository.SongAlbumRepository;
import org.music_player.web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongAlbumRepository songAlbumRepository;

    public AlbumDTO convertAlbumEntityToDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setAlbumId(album.getAlbumId());
        albumDTO.setName(album.getAlbumName());
        albumDTO.setImg(album.getAlbumImg());
        List<String> commonSongImg = new ArrayList<>();
        for (SongAlbum songAlbum : songAlbumRepository.findCommonSongByAlbum(album.getAlbumId())) {
            commonSongImg.add(songRepository.findBySongId(songAlbum.getSong().getSongId()).getSongImg());
        }
        albumDTO.setCommonSongImg(commonSongImg);
        return albumDTO;
    }

    public List<AlbumDTO> listAllAlbum() {
        List<AlbumDTO> listAllAlbum = new ArrayList<>();
        for (Album album : albumRepository.findAllAlbum()) {
            listAllAlbum.add(convertAlbumEntityToDTO(album));
        }
        return listAllAlbum;
    }

    public void addAlbum(Album album, MultipartFile albumImg) throws IOException {
        if (albumImg == null || albumImg.isEmpty()) {
            throw new IOException("Ảnh album không được để trống");
        }
        // Lấy đường dẫn để thêm nhạc và ảnh vào
        String imgUploadDir = "./src/main/resources/static/assets/img/album/" + albumImg.getOriginalFilename();
        Path imgPath = Paths.get(imgUploadDir);
        Files.write(imgPath, albumImg.getBytes());

        album.setAlbumImg(imgUploadDir.replace("./src/main/resources/static", ""));
        boolean isDuplicateImage = imgIsExisted(album.getAlbumImg());

        // Xử lý trường hợp trùng lặp
        if (isDuplicateImage) {
            System.out.println("Trùng lặp");
        } else {
            albumRepository.save(album);
        }
    }
    @Transactional
    public void updateAlbum(Album album) {
        albumRepository.save(album);
        albumRepository.flush();
    }

    public void deleteAlbum(Integer albumId) {
        Album album = albumRepository.getReferenceById(albumId);
        deleteFile("./src/main/resources/static" + album.getAlbumImg());
        albumRepository.deleteById(albumId);
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

    public boolean imgIsExisted(String img) {
        return albumRepository.existsByAlbumImg(img);
    }

    public void saveAlbum(Album album) {
        albumRepository.save(album);
    }

    public Album findByAlbumId(Integer albumId) {
        return albumRepository.findByAlbumId(albumId);
    }
}
