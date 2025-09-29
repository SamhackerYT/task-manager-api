    package com.example.taskmanagerapi.config.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagerapi.config.controller.dto.LoginRequest;
import com.example.taskmanagerapi.config.controller.dto.RegisterRequest;
import com.example.taskmanagerapi.config.controller.dto.entity.User;

import java.util.HashMap;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtil jwtUtil;

        @PostMapping("/register")
        public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            userService.saveUser (user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User  registered successfully");
            return ResponseEntity.ok(response);
        }

        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        }
    }
    