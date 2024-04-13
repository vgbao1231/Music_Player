package org.music_player.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "playlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Playlist {
    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playlistId;
    @Column(name = "playlist_name")
    private String playlistName;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE)
    private List<SongPlaylist> songPlaylists;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

}
