package org.music_player.web.service;

import org.music_player.web.dto.AlbumDTO;
import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Song;
import org.music_player.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public AlbumDTO convertAlbumEntityToDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setAlbumId(album.getAlbumId());
        albumDTO.setAlbumName(album.getAlbumName());
        albumDTO.setAlbumImg(album.getAlbumImg());
        return albumDTO;
    }
    public List<AlbumDTO> listAllAlbum() {
        List<AlbumDTO> listAllAlbum = new ArrayList<>();
        for (Album album : albumRepository.findAllAlbum()) {
            listAllAlbum.add(convertAlbumEntityToDTO(album));
        }
        return listAllAlbum;
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
