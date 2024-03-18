package org.music_player.web.dto;

import lombok.*;
import org.music_player.web.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlaylistDTO {
    private Integer playlistId;
    private String title;
    private User user;
}
