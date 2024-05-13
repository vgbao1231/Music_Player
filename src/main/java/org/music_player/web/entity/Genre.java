package org.music_player.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Tên thể loại không được để trống")
    private String genreName;
    @Column(name = "genre_img")
    private String genreImg;
}
