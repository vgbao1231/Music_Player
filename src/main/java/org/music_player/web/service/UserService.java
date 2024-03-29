package org.music_player.web.service;

import org.music_player.web.entity.User;
import org.music_player.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
}
