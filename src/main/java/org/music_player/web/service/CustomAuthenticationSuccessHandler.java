package org.music_player.web.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            response.sendRedirect("/admin"); // Chuyển hướng tới trang admin nếu có quyền admin
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof CustomUserDetails customUserDetails) {
                    request.getSession().setAttribute("userId", customUserDetails.getUser().getUserId());
                }
            }
            response.sendRedirect("/user"); // Chuyển hướng tới trang user nếu có quyền user
        }
    }
}
