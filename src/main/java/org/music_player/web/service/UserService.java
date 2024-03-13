package org.music_player.web.service;

import org.music_player.web.entity.User;

public interface UserService {
    User findByUserName(String userName);
}
