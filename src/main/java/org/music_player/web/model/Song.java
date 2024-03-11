package org.music_player.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "song")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Song {
    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songId;
    @Column(name = "title")
    private String title;
    @Column(name = "artist_id")
    private String artistId;

    @OneToMany(mappedBy = "song",fetch = FetchType.EAGER)
    private Set<SongGenres> songGenres;
    @OneToMany(mappedBy = "song",fetch = FetchType.EAGER)
    private Set<SongAlbums> songAlbums;
    @OneToMany(mappedBy = "song",fetch = FetchType.EAGER)
    private Set<SongPlaylists> songPlaylists;

}
