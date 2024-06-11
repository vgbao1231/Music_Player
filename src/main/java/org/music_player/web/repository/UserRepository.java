package org.music_player.web.repository;

import org.music_player.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    User findByUserId(Integer userId);
    Optional<User> findByEmail(String email);
}
