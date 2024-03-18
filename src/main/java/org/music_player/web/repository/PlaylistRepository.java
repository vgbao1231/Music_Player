package org.music_player.web.repository;

import org.music_player.web.entity.Playlist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    @Query(value = "select * from playlist where playlist.user_id = ?1", nativeQuery = true)
    List<Playlist> findAllByUserId(Integer userId);
    Playlist findByTitle(String title);
}
