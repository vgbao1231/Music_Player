package org.music_player.web.repository;

import org.music_player.web.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Integer> {
    @Query(value = "select * from song", nativeQuery = true)
    List<Song> findAllSong();
    Song findBySongId(Integer songId);
    boolean existsBySongImg(String img);
    boolean existsByAudio(String audio);
}
