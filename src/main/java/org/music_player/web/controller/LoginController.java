package org.music_player.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {
    @RequestMapping(value = {"/login","/"}, method = RequestMethod.GET)
    public String login() {
        // Kiểm tra xem người dùng đã đăng nhập hay không
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // Nếu đã đăng nhập, điều hướng người dùng về trang chủ
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                return "redirect:/admin"; // Chuyển hướng tới trang admin nếu có quyền admin
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                return "redirect:/user"; // Chuyển hướng tới trang user nếu có quyền user
            }
        }
        // Nếu chưa đăng nhập, hiển thị trang đăng nhập
        return "login";
    }
}
