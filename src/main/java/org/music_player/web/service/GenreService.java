package org.music_player.web.service;

import org.music_player.web.dto.GenreDTO;
import org.music_player.web.entity.Genre;
import org.music_player.web.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    public GenreDTO convertGenreEntityToDTO(Genre genre){
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setGenreId(genre.getGenreId());
        genreDTO.setGenreName(genre.getGenreName());
        genreDTO.setGenreImg(genre.getGenreImg());
        return genreDTO;
    }
    public Genre convertGenreDTOToEntity(GenreDTO genreDTO){
        Genre genre = new Genre();

        genre.setGenreId(genreDTO.getGenreId());
        genre.setGenreName(genreDTO.getGenreName());
        genre.setGenreImg(genreDTO.getGenreImg());
        return genre;
    }
    public List<GenreDTO> listALlGenre(){
        List<GenreDTO> listAllGenre = new ArrayList<>();
        System.out.println("List DTO: ");
        for(Genre genre : genreRepository.findAllGenre()){
            listAllGenre.add(convertGenreEntityToDTO(genre));
        }
        return listAllGenre;
    }
    public Genre findGenreByGenreId(Integer genreId){
        return genreRepository.findGenreByGenreId(genreId);
    }
}
