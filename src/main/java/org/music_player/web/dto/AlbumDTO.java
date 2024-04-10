package org.music_player.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AlbumDTO {
    private Integer albumId;
    private String albumName, AlbumImg;
}
