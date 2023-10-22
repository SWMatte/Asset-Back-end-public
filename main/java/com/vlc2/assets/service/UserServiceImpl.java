package com.vlc2.assets.service;

import com.vlc2.assets.dto.request.ChangePasswordDto;
import com.vlc2.assets.dto.response.ChangePasswordResponse;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User findById(int id) {
        log.info("Starting method findById in class: " +getClass());
        User theUser = null;
        try{
            log.info("Finding user with id : " +id);
            User user = userRepository.findById(id).orElseThrow();
            log.info("User found!!");
            theUser = user;
            log.info("End of the method findById in class: " +getClass());
            return theUser;
        }catch (EntityNotFoundException eN){
            log.error("Can't find the user");
            return theUser;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User save(User user) {
        log.info("Starting method save in class: " +getClass());
        User theUser = null;
        try {
            log.info("Saving the User :");
            theUser = userRepository.save(user);
            log.info("The user is successfully saved!");
            log.info("End of the method save in class: " + getClass());
            return theUser;
        }catch (NullPointerException e){
            log.error("Can't save the user on database!");
            return theUser;
        }
    }

    @Override
    public User loadUserById(String id) {
        Optional<User> user = userRepository.findById(Integer.valueOf(id));
        if (user.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordDto changePasswordDto,User user) {
        log.info("Trying to save the new password...");
         if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
             log.error("Sorry! The old password does not match!" );
             throw new RuntimeException("The old password does not match, can't proceed with the change!");
         }
         if(passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())){
             log.error("Error! The new password can't be equals to the old one!");
             return ChangePasswordResponse.builder().message(false).build();
         }
         log.info("Encrypting the new password!");
         try{
             user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
             userRepository.save(user);
             log.info("Password saved!");
         } catch (Exception e) {
             log.error("Error updating the user: " + e.getMessage());
             throw new RuntimeException("Erroro");
        }
        return ChangePasswordResponse.builder().message(true).build();
    }
}
