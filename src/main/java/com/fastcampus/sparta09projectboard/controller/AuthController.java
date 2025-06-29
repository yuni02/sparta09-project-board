package com.fastcampus.sparta09projectboard.controller;

import com.fastcampus.sparta09projectboard.dto.request.SignupRequest;
import com.fastcampus.sparta09projectboard.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class AuthController {
    
    private final UserAccountService userAccountService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signupPage() {
        return "sign-up";
    }
    
    @PostMapping("/signup")
    public String signup(SignupRequest signupRequest, RedirectAttributes redirectAttributes) {
        try {
            userAccountService.saveUser(signupRequest.toDto());
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다!");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }
    }
} 