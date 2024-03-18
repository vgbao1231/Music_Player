package org.music_player.web.repository;

import org.music_player.web.entity.Song;
import org.music_player.web.entity.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist,Integer> {
    @Query(value = "select * from song_playlist sp where sp.playlist_id = ?1", nativeQuery = true)
    List<Song> listAllSongByPlaylist(Integer playlistId);
}
