package org.music_player.web.model;

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

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER)
    private Set<SongAlbums> songAlbums;
}
