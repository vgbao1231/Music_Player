package org.music_player.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "song_playlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongPlaylist {
    @Id
    @Column(name = "song_playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songPlaylistId;
    @ManyToOne
    @JoinColumn(name = "song_id",referencedColumnName = "song_id")
    private Song song;
    @ManyToOne
    @JoinColumn(name = "playlist_id",referencedColumnName = "playlist_id")
    private Playlist playlist;
}
