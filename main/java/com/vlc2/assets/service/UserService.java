package com.vlc2.assets.service;

import com.vlc2.assets.dto.request.ChangePasswordDto;
import com.vlc2.assets.dto.response.ChangePasswordResponse;
import com.vlc2.assets.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    User findById(int id);
    Optional<User> findByEmail(String email);
    User save(User user);
    User loadUserById(String id);
    ChangePasswordResponse changePassword(ChangePasswordDto changePasswordDto,User user);
}
