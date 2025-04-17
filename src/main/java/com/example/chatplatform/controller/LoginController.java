package com.example.chatplatform.controller;

import com.example.chatplatform.model.User;
import com.example.chatplatform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Display the login page.
     *
     * @param model Spring's model to pass data to the view.
     * @return The name of the login HTML template.
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("error", false); // Add a default error flag
        return "login"; // Serves the login.html template
    }

    /**
     * Handle login form submission.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param session  The HTTP session to store user information.
     * @param model    Spring's model to pass data to the view.
     * @return The name of the next HTML template or a redirect URL.
     */
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        // Authenticate the user
        User user = userService.authenticate(username, password);

        if (user == null) {
            // Authentication failed, show login page with error
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login"; // Return to the login page
        }

        // Store user details in the session
        session.setAttribute("role", user.getRole());
        session.setAttribute("username", user.getUsername());

        // Redirect to the home page or another secure page
        return "redirect:/home";
    }

    /**
     * Handle logout and invalidate the session.
     *
     * @param session The HTTP session to be invalidated.
     * @return Redirect to the login page after logout.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/login?logout"; // Redirect to login page with a logout message
    }
}