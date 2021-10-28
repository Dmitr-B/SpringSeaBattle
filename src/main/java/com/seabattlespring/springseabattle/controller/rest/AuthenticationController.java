//package com.seabattlespring.springseabattle.controller.rest;
//
//import com.seabattlespring.springseabattle.dto.UserDto;
//import com.seabattlespring.springseabattle.repository.UserRepository;
//import com.seabattlespring.springseabattle.repository.domain.User;
//import com.seabattlespring.springseabattle.security.JwtTokenProvider;
//import jdk.jshell.spi.ExecutionControl;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@Log4j2
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthenticationController {
//
//    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
//            User user = userRepository.findByUserName(userDto.getUserName()).orElseThrow(
//                    () -> new UsernameNotFoundException("User doesn`t exists"));
//            String token = jwtTokenProvider.createToken(userDto.getUserName(), user.getRole().name());
//            Map<Object, Object> response = new HashMap<>();
//            response.put("userName", userDto.getUserName());
//            response.put("token", token);
//
//            return ResponseEntity.ok(response);
//
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid userName/password combination");
//        }
//    }
//
//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);
//    }
//}
