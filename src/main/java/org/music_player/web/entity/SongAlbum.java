package org.music_player.web.entity;

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
public class SongAlbum {
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
