package org.music_player.web.service;

import org.music_player.web.dto.GenreDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Genre;
import org.music_player.web.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    public GenreDTO convertGenreEntityToDTO(Genre genre){
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreId(genre.getGenreId());
        genreDTO.setName(genre.getGenreName());
        genreDTO.setImg(genre.getGenreImg());
        return genreDTO;
    }
    public List<GenreDTO> listALlGenre(){
        List<GenreDTO> listAllGenre = new ArrayList<>();
        for(Genre genre : genreRepository.findAllGenre()){
            listAllGenre.add(convertGenreEntityToDTO(genre));
        }
        return listAllGenre;
    }
    public void deleteGenre(Integer genreId) {
        Genre genre = genreRepository.getReferenceById(genreId);
        deleteFile("./src/main/resources/static" + genre.getGenreImg());
        genreRepository.deleteById(genreId);
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
    public Genre findByGenreId(Integer genreId){
        return genreRepository.findByGenreId(genreId);
    }
    public boolean imgIsExisted(String img) {
        return genreRepository.existsByGenreImg(img);
    }
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }
}
