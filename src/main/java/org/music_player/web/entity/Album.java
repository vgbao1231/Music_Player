package org.music_player.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Table(name = "album")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Album {
    @Id
    @Column(name = "album_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer albumId;
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "album_img")
    private String albumImg;

    @OneToMany(mappedBy = "album", cascade = CascadeType.REMOVE)
    private Set<SongAlbum> songAlbums;
}
