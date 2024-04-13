package org.music_player.web.service;

import org.music_player.web.dto.PlaylistDTO;
import org.music_player.web.entity.Playlist;
import org.music_player.web.entity.Song;
import org.music_player.web.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    public PlaylistDTO convertPlaylistEntityToDTO(Playlist playlist){
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setPlaylistId(playlist.getPlaylistId());
        playlistDTO.setPlaylistName(playlist.getPlaylistName());
        playlistDTO.setUser(playlist.getUser());
        return playlistDTO;
    }
    public List<PlaylistDTO> listALlPlaylist(Integer userId){
        List<PlaylistDTO> listAllPlaylist = new ArrayList<>();
        for(Playlist playlist : playlistRepository.findAllByUserId(userId)){
            listAllPlaylist.add(convertPlaylistEntityToDTO(playlist));
        }
        return listAllPlaylist;
    }
    public Playlist getPlaylistById(Integer playlistId) {
        return playlistRepository.getReferenceById(playlistId);
    }
    public void savePlaylist(Playlist playlist){
        playlistRepository.save(playlist);
    }
    public void deletePlaylist(Integer playlistId){
        playlistRepository.deleteById(playlistId);
    }
}
