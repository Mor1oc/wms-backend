package com.warehouse.WMS.security.jwt;

import com.warehouse.WMS.security.WmsUserDetailsService;
import com.warehouse.WMS.security.model.UserDTO;
import com.warehouse.WMS.security.model.WmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final AuthenticationManager manager;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(AuthenticationManager manager) {
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
//        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            );

            Authentication auth = manager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwtToken = JwtUtil.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(jwtToken);
//        } catch (Exception e) {
//            logger.error("Authentication failed: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
//        }
    }
}
