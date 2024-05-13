package org.music_player.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "song_name")
    @NotBlank(message = "Tên bài hát không được để trống")
    private String songName;
    @Column(name = "artist")
    @NotBlank(message = "Tên nghệ sĩ không được để trống")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "genre_id",referencedColumnName = "genre_id")
    private Genre genre;

    @Column(name = "song_img")
    private String songImg;
    @Column(name = "song_audio")
    private String songAudio;

    @OneToMany(mappedBy = "song", cascade = CascadeType.REMOVE)
    private Set<SongAlbum> songAlbum;
    @OneToMany(mappedBy = "song", cascade = CascadeType.REMOVE)
    private Set<SongPlaylist> songPlaylist;
}
