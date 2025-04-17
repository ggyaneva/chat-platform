package com.example.chatplatform.controller;

import com.example.chatplatform.model.User;
import com.example.chatplatform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private static final String REDIRECT_HOME = "redirect:/home";
    private static final String REDIRECT_LOGIN = "redirect:/login?logout";

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.authenticate(username, password);

            if (user == null) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Invalid username or password");
                return "login";
            }

            session.setAttribute("role", user.getRole());
            session.setAttribute("username", user.getUsername());

            return REDIRECT_HOME;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during login. Please try again.");
            return REDIRECT_LOGIN;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return REDIRECT_LOGIN;
    }
}