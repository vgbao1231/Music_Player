package org.music_player.web.service;

import org.music_player.web.dto.SongDTO;
import org.music_player.web.entity.Song;
import org.music_player.web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    public SongDTO convertSongEntityToDTO(Song song){
        SongDTO songDTO = new SongDTO();

        songDTO.setSongId(song.getSongId());
        songDTO.setTitle(song.getTitle());
        songDTO.setGenre(song.getGenre());
        songDTO.setArtist(song.getArtist());
        songDTO.setAudio(song.getAudio());
        songDTO.setSongImg(song.getSongImg());
        return songDTO;
    }
    public Song convertSongDTOToEntity(SongDTO songDTO){
        Song song = new Song();
        song.setSongId(songDTO.getSongId());
        song.setTitle(songDTO.getTitle());
        song.setGenre(songDTO.getGenre());
        song.setArtist(songDTO.getArtist());
        song.setAudio(songDTO.getAudio());
        song.setSongImg(songDTO.getSongImg());
        return song;
    }
    public List<SongDTO> listALlSong(){
        List<SongDTO> listAllSong = new ArrayList<>();
        for(Song song : songRepository.findAllSong()){
            listAllSong.add(convertSongEntityToDTO(song));
        }
        return listAllSong;
    }

    public String encodingFileToString(MultipartFile multipartFile){
        String base64 = null;
        try {
            base64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        return base64;
    }
    public Song getSongById(int id){
        return songRepository.getReferenceById(id);
    }
    public void saveSong(Song song){
        songRepository.save(song);
    }
    public void deleteSong(Integer songId){
        songRepository.deleteById(songId);
    }
}
