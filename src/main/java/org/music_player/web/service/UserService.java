package org.music_player.web.service;

import org.music_player.web.model.User;

public interface UserService {
    User findByUsername(String username);
}
