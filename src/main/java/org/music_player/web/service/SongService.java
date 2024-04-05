package org.music_player.web.service;

import org.music_player.web.dto.SongDTO;
import org.music_player.web.dto.SongPlaylistDTO;
import org.music_player.web.entity.Playlist;
import org.music_player.web.entity.Song;
import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.PlaylistRepository;
import org.music_player.web.repository.SongPlaylistRepository;
import org.music_player.web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongPlaylistRepository songPlaylistRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public SongDTO convertSongEntityToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setSongId(song.getSongId());
        songDTO.setTitle(song.getTitle());
        songDTO.setGenre(song.getGenre());
        songDTO.setArtist(song.getArtist());
        songDTO.setAudio(song.getAudio());
        songDTO.setSongImg(song.getSongImg());
        return songDTO;
    }

    public String encodingFileToString(MultipartFile multipartFile) {
        String base64 = null;
        try {
            base64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public List<SongDTO> listALlSong() {
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
                    (songRepository.findAllSongByPlaylist(songPlaylist.getSong().getSongId())));
        }
        return listAllSongByPlaylist;
    }

    public void addSongToPlaylist(Integer songId, Integer playlistId) {
        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setSong(getSongById(songId));
        songPlaylist.setPlaylist(playlistRepository.findByPlaylistId(playlistId));
        try {
            songPlaylistRepository.save(songPlaylist);
        } catch (Exception e) {
            System.out.println("Bài hát đã có trong playlist");
        }
    }

    public boolean imgIsExisted(String img) {
        return songRepository.existsBySongImg(img);
    }

    public boolean audioIsExisted(String audio) {
        return songRepository.existsByAudio(audio);
    }

    public Song getSongById(Integer songId) {
        return songRepository.getReferenceById(songId);
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
