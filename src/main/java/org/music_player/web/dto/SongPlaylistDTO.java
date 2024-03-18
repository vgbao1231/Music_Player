package org.music_player.web.dto;

import lombok.*;
import org.music_player.web.entity.Playlist;
import org.music_player.web.entity.Song;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SongPlaylistDTO {
    private Playlist playlist;
    private Song song;
}
