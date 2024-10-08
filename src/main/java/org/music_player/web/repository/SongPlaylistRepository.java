package org.music_player.web.repository;

import org.music_player.web.entity.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist,Integer> {
    @Query(value = "select * from song_playlist sp where sp.playlist_id = ?1", nativeQuery = true)
    List<SongPlaylist> listAllSongByPlaylist(Integer playlistId);
    @Query(value = "select * from song_playlist sp where sp.playlist_id = ?1 AND sp.song_id = ?2", nativeQuery = true)
    SongPlaylist findByPlaylistIdAndSongId(Integer playlistId,Integer songId);
}
