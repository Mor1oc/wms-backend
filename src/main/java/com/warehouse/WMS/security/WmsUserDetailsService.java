package com.warehouse.WMS.security;

import com.warehouse.WMS.security.jwt.LoginController;
import com.warehouse.WMS.security.model.WmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WmsUserDetailsService implements UserDetailsService {
    private final WmsUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(WmsUserDetailsService.class);

    public WmsUserDetailsService(WmsUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername: {}", username);
        Optional<WmsUser> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            WmsUser user = optionalUser.get();
            logger.info("user: {}", user);
            return User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().getRole().getTittle())
                    .build();
        }

        throw new UsernameNotFoundException(username);
    }
}
