package org.music_player.web.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AlbumDTO {
    private Integer albumId;
    private String name, img;
    private List<String> commonSongImg;
}
