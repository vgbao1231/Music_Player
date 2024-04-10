package org.music_player.web.repository;

import org.music_player.web.entity.SongAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongAlbumRepository extends JpaRepository<SongAlbum,Integer> {
    @Query(value = "select * from song_album sa where sa.album_id = ?1", nativeQuery = true)
    List<SongAlbum> listAllSongByAlbum(Integer playlistId);
    @Query(value = "select * from song_album sa where sa.album_id = ?1 AND sa.song_id = ?2", nativeQuery = true)
    SongAlbum findByAlbumIdAndSongId(Integer playlistId,Integer songId);
}
