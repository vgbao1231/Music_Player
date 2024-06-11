package org.music_player.web.repository;

import org.music_player.web.entity.OTP;
import org.music_player.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByUser(User user);
}
