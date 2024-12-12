package com.warehouse.WMS.security;

import com.warehouse.WMS.security.model.Role;
import com.warehouse.WMS.security.model.UserRoles;
import com.warehouse.WMS.security.model.WmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CreateNewUserController {

    private static final Logger logger = LoggerFactory.getLogger(CreateNewUserController.class);

    private final PasswordEncoder passwordEncoder;

    private final WmsUserRepository customUserRepository;

    public CreateNewUserController(PasswordEncoder passwordEncoder, WmsUserRepository customUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customUserRepository = customUserRepository;
    }

    @PostMapping("/createnewuser")
    public ResponseEntity<String> createNewUser(@RequestBody WmsUser user) {
//        logger.info("Creating new user");
        Optional<WmsUser> optionalUser = customUserRepository.findById(user.getUsername());
        if (optionalUser.isEmpty()) {
            String role = user.getRole().getRole().getTittle();
            customUserRepository.save(new WmsUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), new Role(UserRoles.fromStringToId(role), UserRoles.fromString(role))));
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().body("Failure");
    }
}
