package org.music_player.web.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.music_player.web.entity.User;
import org.music_player.web.service.OTPService;
import org.music_player.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class PublicController {
    @Autowired
    private OTPService otpService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String loginForm() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa?
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
        return "/public/login";
    }

    @RequestMapping("/register")
    public String registerForm() {
        return "/public/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String verifyPassword,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.addUser(email, username, password, verifyPassword);
            otpService.sendOTP(email);
            redirectAttributes
                .addFlashAttribute("success", "Mã OTP đã được gửi về email để xác minh tài khoản");
            session.setAttribute("user", user);
            return "redirect:verify-otp";
        } catch (Exception e) {
            redirectAttributes
                .addFlashAttribute("error", e.getMessage());
        }
        return "redirect:register";
    }

    @RequestMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "/public/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByEmail(email);
        User u;
        if (user.isPresent()) u = user.get();
        else {
            redirectAttributes
                .addFlashAttribute("error", "Tài khoản này không tồn tại");
            return "redirect:verify-otp";
        }
        session.setAttribute("user", u);
        redirectAttributes
            .addFlashAttribute("success", "Mã OTP đã được gửi về email để xác minh tài khoản");
        otpService.sendOTP(email);
        return "redirect:verify-otp";
    }

    @RequestMapping("/verify-otp")
    public String verifyOTPForm(HttpSession session, Model model) {
        String flashMessage = (String) session.getAttribute("flashMessage");
        if (flashMessage != null) {
            model.addAttribute("success", flashMessage);
            session.removeAttribute("flashMessage");
        }
        return "/public/verify-otp";
    }

    @Transactional
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam String otp,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng trong phiên làm việc.");
                return "redirect:/verify-otp";
            }
            boolean isValid = otpService.verifyOTP(user.getEmail(), otp);
            if (isValid) {
                if (!user.getStatus()) {
                    user.setStatus(true); // Kích hoạt tài khoản
                    userService.saveUser(user);
                    session.removeAttribute("user");
                    redirectAttributes.addFlashAttribute("success", "Xác minh OTP thành công");
                    return "redirect:/login";
                } else {
                    redirectAttributes.addFlashAttribute("success", "Xác minh OTP thành công");
                    return "redirect:/reset-password";
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/verify-otp";
    }

    @PostMapping("/send-otp-again")
    public String sendOTPAgain(HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng trong phiên làm việc.");
                return "redirect:/login";
            }
            otpService.sendOTP(user.getEmail());
            redirectAttributes.addFlashAttribute("success", "Mã OTP đã được gửi lại về email.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/verify-otp";
    }

    @RequestMapping("/reset-password")
    public String resetPasswordForm() {
        return "/public/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password,
                                @RequestParam String verifyPassword,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            if (!password.equals(verifyPassword))
                throw new Exception("Nhập lại mật khẩu không trùng khớp");
            User user = (User) session.getAttribute("user");
            userService.resetPassword(user, password);
            redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reset-password";
    }
}