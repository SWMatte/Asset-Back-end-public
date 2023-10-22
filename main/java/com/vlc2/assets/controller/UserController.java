package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.dto.request.ChangePasswordDto;
import com.vlc2.assets.dto.response.ChangePasswordResponse;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @Authorized
    public ResponseEntity<?> getUser(User user) {
        try{
            return ResponseEntity.ok(user);
        }catch (Exception e){
            log.error("Can't find the user: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Authorized
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(User user, @RequestBody ChangePasswordDto changePasswordDto){
        log.info("Start changePassword in UserController...");
        try{
            ChangePasswordResponse updated = userService.changePassword(changePasswordDto, user);
            log.info("End change password in User Controller!");
            return ResponseEntity.ok(updated);
        }catch (Exception e){
            log.error("Error changePassword in UserController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

}
