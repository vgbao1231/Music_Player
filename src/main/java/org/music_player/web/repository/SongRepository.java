package org.music_player.web.repository;

import org.music_player.web.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Integer> {
    //Ví dụ native SQL
    //@Query(value = "select * from User u where u.email_address = ?1", nativeQuery = true)
    @Query(value = "select * from song", nativeQuery = true)
    List<Song> findAllSong();
    @Query(value = "select * from song s where s.song_id = ?1", nativeQuery = true)
    Song findAllSongByPlaylist(Integer songId);
    boolean existsBySongImg(String img);
    boolean existsByAudio(String audio);
}
