package org.music_player.web.service;

import org.music_player.web.entity.Album;
import org.music_player.web.entity.SongAlbum;
import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.SongAlbumRepository;
import org.music_player.web.repository.SongPlaylistRepository;
import org.music_player.web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SongAlbumService {
    @Autowired
    private SongService songService;
    @Autowired
    SongAlbumRepository songAlbumRepository;

    public void addSongAlbum(Integer songId, Album album) throws IOException {
        SongAlbum songAlbum = new SongAlbum();
        songAlbum.setSong(songService.findBySongId(songId));
        songAlbum.setAlbum(album);
        songAlbumRepository.save(songAlbum);
    }

    public void deleteSongFromAlbum(Integer songId, Album album) {
        SongAlbum songAlbum = songAlbumRepository.findByAlbumIdAndSongId(album.getAlbumId(), songId);
        songAlbumRepository.delete(songAlbum);
    }
}
