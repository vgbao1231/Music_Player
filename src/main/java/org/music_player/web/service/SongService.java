package org.music_player.web.service;

import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Song;
import org.music_player.web.entity.SongAlbum;
import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
        songDTO.setAudio(song.getAudio());
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
        songPlaylist.setSong(findBySongId(songId));
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
        return songRepository.existsByAudio(audio);
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
        deleteFile("./src/main/resources/static" + song.getAudio());
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
