package org.music_player.web.dto;

import lombok.*;
import org.music_player.web.entity.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GenreDTO {
    private Integer genreId;
    private String name, img;
}
