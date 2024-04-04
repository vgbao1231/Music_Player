package org.music_player.web.entity;

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
    @Column(name = "artist")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "genre_id",referencedColumnName = "genre_id")
    private Genre genre;

    @Column(name = "song_img", columnDefinition = "MEDIUMBLOB")
    private String songImg;
    @Column(name = "audio", columnDefinition = "MEDIUMBLOB")
    private String audio;

    @OneToMany(mappedBy = "song")
    private Set<SongAlbum> songAlbum;
    @OneToMany(mappedBy = "song")
    private Set<SongPlaylist> songPlaylist;

}
