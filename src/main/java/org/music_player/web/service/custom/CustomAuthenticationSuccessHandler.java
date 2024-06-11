package org.music_player.web.service.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.User;
import jakarta.servlet.http.HttpSession;
import org.music_player.web.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private OTPService otpService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            response.sendRedirect("/admin"); // Chuyển hướng tới trang admin nếu có quyền admin
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            // Kiểm tra tài khoản đã xác minh chưa
            if (!user.getStatus()) {
                // Gửi lại mã OTP
                otpService.sendOTP(user.getEmail());

                // Thêm thông báo vào session
                HttpSession session = request.getSession();
                session.setAttribute("flashMessage", "Mã OTP đã được gửi lại về email để xác minh.");
                session.setAttribute("user", user);

                response.sendRedirect("/verify-otp"); // Chuyển hướng tới trang xác minh OTP
            } else {
                response.sendRedirect("/user"); // Chuyển hướng tới trang user nếu có quyền user
            }
        }
    }
}
