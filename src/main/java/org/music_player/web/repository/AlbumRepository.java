package org.music_player.web.repository;

import org.music_player.web.entity.Album;
import org.music_player.web.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    @Query(value = "select * from album", nativeQuery = true)
    List<Album> findAllAlbum();

    boolean existsByAlbumImg(String img);
}
