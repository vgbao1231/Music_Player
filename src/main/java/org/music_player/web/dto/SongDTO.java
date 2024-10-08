package org.music_player.web.dto;
import lombok.*;
import org.music_player.web.entity.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SongDTO {
    private Integer songId;
    private Genre genre;
    private String songName, artist, songImg, audio;
}