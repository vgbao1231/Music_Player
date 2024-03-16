package org.music_player.web.repository;

import org.music_player.web.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
    @Query(value = "select * from genre", nativeQuery = true)
    List<Genre> findAllGenre();
    Genre findGenreByGenreId(Integer genreId);
}

