package org.music_player.web.repository;

import org.music_player.web.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Integer> {
    @Query(value = "select * from song", nativeQuery = true)
    List<Song> findAllSong();
    @Query(value = "select * from song s where s.genre_id = ?1", nativeQuery = true)
    List<Song> findAllSongByGenreId(Integer genreId);
    Song findBySongId(Integer songId);
    boolean existsBySongImg(String img);
    boolean existsBySongAudio(String audio);
}
