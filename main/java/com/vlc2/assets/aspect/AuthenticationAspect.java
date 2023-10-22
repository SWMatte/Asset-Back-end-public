package com.vlc2.assets.aspect;

import com.vlc2.assets.dto.response.AuthenticationResponse;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.service.JwtService;
import com.vlc2.assets.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
@Slf4j
public class AuthenticationAspect {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    HttpServletRequest request;

    public AuthenticationAspect(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Pointcut("@annotation(Authorized)")
    private void AuthenticateMethod() {
    }

    @Around("AuthenticateMethod()")
    public Object AuthenticationMethod(ProceedingJoinPoint joinPoint ) throws Throwable {
        log.info("Start of the method AuthenticationMethod  in class : " +getClass() );
        log.info("Taking the request Header");
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Token is missing").build());
        }
        var jwt = authHeader.substring(7); //substring per escludere bearer più uno spazio "bearer "
        if(!jwtService.isTokenValid(jwt)){
            log.error("The Token is not valid. Verified in class: " + getClass());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Token not valid!").build());
        }
            log.info("The Token is valid!");
            log.info("Extracting the id from the token");
            int id = Integer.parseInt(jwtService.extractStringId(jwt));
            log.info("Finding the user with the extracted id!");
            User userById = null;
            try{
                userById = userService.findById(id);
                if(userById.isDeleteFlag()){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("User not found").build());
                }
                log.info("The user is found!");
            }catch(NullPointerException e){
                log.error("User not found!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("User not found").build());
            }
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof User) {
                    args[i] = userById;
                }
            }
            log.info("Passing the user to the controller method");
            return joinPoint.proceed(args);
    }
}


