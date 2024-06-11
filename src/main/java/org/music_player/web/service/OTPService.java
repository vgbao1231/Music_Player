package org.music_player.web.service;

import org.music_player.web.entity.OTP;
import org.music_player.web.entity.User;
import org.music_player.web.repository.OTPRepository;
import org.music_player.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    private String generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    public void sendOTP(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String otp = generateOTP();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(1);

            OTP otpEntity = otpRepository.findByUser(user);
            if (otpEntity == null) {
                otpEntity = new OTP();
                otpEntity.setUser(user);
            }
            otpEntity.setOtp(otp);
            otpEntity.setExpiredTime(expiresAt);

            otpRepository.save(otpEntity);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);
            mailSender.send(message);
        }
    }

    public boolean verifyOTP(String email, String otp) throws IOException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IOException("Tài khoản không tồn tại");
        }
        User u = userOptional.get();
        OTP otpEntity = otpRepository.findByUser(u);
        if (otpEntity == null)
            throw new IOException("Chưa có mã OTP để xác minh tài khoản này");
        if (!otpEntity.getOtp().equals(otp))
            throw new IOException("Mã OTP không đúng");
        if (otpEntity.getExpiredTime().isBefore(LocalDateTime.now())) {
            deleteOTP(otpEntity);
            throw new IOException("Mã OTP đã hết hạn");
        }
        deleteOTP(otpEntity);
        return true;
    }

    public void deleteOTP (OTP otp){
        otpRepository.delete(otp);
    }
}
