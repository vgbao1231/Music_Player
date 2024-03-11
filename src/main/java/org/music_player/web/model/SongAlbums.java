package org.music_player.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "song_album")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongAlbums {
    @Id
    @Column(name = "song_album_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songAlbumId;
    @ManyToOne
    @JoinColumn(name = "song_id",referencedColumnName = "song_id")
    private Song song;
    @ManyToOne
    @JoinColumn(name = "album_id",referencedColumnName = "album_id")
    private Album album;
}
