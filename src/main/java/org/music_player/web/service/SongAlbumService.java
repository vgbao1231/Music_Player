package org.music_player.web.service;

import org.music_player.web.entity.SongAlbum;
import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.SongAlbumRepository;
import org.music_player.web.repository.SongPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongAlbumService {
    @Autowired
    SongAlbumRepository songAlbumRepository;
    public void deleteSongFromAlbum(Integer albumId, Integer songId){
        SongAlbum songAlbum = songAlbumRepository.findByAlbumIdAndSongId(albumId,songId);
        songAlbumRepository.delete(songAlbum);
    }
}
