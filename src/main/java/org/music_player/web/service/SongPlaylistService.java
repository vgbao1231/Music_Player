package org.music_player.web.service;

import org.music_player.web.entity.SongPlaylist;
import org.music_player.web.repository.SongPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongPlaylistService {
    @Autowired
    SongPlaylistRepository songPlaylistRepository;
    public void deleteSongFromPlaylist(Integer playlistId, Integer songId){
        SongPlaylist songPlaylist = songPlaylistRepository.findByPlaylistIdAndSongId(playlistId,songId);
        songPlaylistRepository.delete(songPlaylist);
    }
}
