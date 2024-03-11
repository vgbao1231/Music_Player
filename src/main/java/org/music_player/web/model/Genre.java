package org.music_player.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "genre")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;
    @Column(name = "genre_name")
    private String genreName;

    @OneToMany(mappedBy = "genre",fetch = FetchType.EAGER)
    private Set<SongGenres> genreSongs;
}
