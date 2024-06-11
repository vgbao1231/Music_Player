package org.music_player.web.service;

import org.music_player.web.entity.User;
import org.music_player.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(String email, String username, String password, String verifyPassword) throws IOException {
        if (email == null)
            throw new IOException("Email không được để trống");
        if (username == null)
            throw new IOException("Tên tài khoản không được để trống");
        if (password == null)
            throw new IOException("Mật khẩu không được để trống");

        if (!password.equals(verifyPassword))
            throw new IOException("Mật khẩu không trùng khớp");
        userRepository.findAll().forEach(u -> {
            if (u.getUserName().equals(username))
                throw new DuplicateKeyException("Tên tài khoản đã được sử dụng");
            if (u.getEmail().equals(email))
                throw new DuplicateKeyException("Email đã được sử dụng");
        });
        User user = User.builder()
            .role("USER")
            .userName(username)
            .status(false)
            .email(email)
            .password(passwordEncoder.encode(password))
            .build();
        saveUser(user);
        return user;
    }

    public void resetPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        saveUser(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
