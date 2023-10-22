package com.vlc2.assets.service;

import com.vlc2.assets.dto.request.AuthenticationRequest;
import com.vlc2.assets.dto.response.AuthenticationResponse;
import com.vlc2.assets.dto.request.RegisterRequest;
import com.vlc2.assets.dto.response.RegisterResponse;
import com.vlc2.assets.dto.response.UpdateResponse;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.entity.UserRole;
import com.vlc2.assets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public RegisterResponse register(RegisterRequest request) throws Exception {
        log.info("Starting method register in class: " + getClass());
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ADMIN)
                .deleteFlag(false)
                .build();
        try {
            log.info("Starting save the user...");
            userRepository.save(user);
            log.info("User saved successfully!");
            return new RegisterResponse(true);
        }catch (Exception e){
            log.error("Can't save the User: " + e.getMessage());
            return new RegisterResponse(false);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Looking for the user");
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if(user.isDeleteFlag() || !passwordEncoder.matches(request.getPassword(),user.getPassword()) ){
            throw new RuntimeException("Bad credential");
        }
        log.info("Calling method Generate Token in JwtService");
        var jwtToken = jwtService.generateToken(user);
        log.info("The token is generated and the user is authenticated!");
        return AuthenticationResponse.builder().token(jwtToken).message("Login successful!").build();
    }
}
