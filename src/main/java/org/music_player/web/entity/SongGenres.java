package org.music_player.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "song_genre")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongGenres {
    @Id
    @Column(name = "song_genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songGenreId;
    @ManyToOne
    @JoinColumn(name = "song_id",referencedColumnName = "song_id")
    private Song song;
    @ManyToOne
    @JoinColumn(name = "genre_id",referencedColumnName = "genre_id")
    private Genre genre;
}
