package by.chaika19.controller.common;

import by.chaika19.entity.User;
import by.chaika19.entity.UserRole;
import by.chaika19.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicAuthorizationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public PublicAuthorizationController(UserService userService, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
        }
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUserAccount(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        String hashedPassword = passwordEncoder.encode(password);
        userService.save(new User(name, email, hashedPassword, UserRole.USER));
        forceAutoLogin(email, request, response);
        return "redirect:/account";
    }

    private void forceAutoLogin(String email, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);
    }
}
