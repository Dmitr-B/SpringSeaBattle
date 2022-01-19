package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.IdLocationDto;
import com.seabattlespring.springseabattle.dto.UserDto;
import com.seabattlespring.springseabattle.player.Role;
import com.seabattlespring.springseabattle.player.Status;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public IdLocationDto createUser(UserDto userDto) throws URISyntaxException {
        User user = new User();
        IdLocationDto idLocationDto = new IdLocationDto();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        //Set<Role> roles = new HashSet<>();
        //roles.add(Role.USER);

        if (userRepository.findByUserName(userDto.getUserName()).isEmpty()) {
            user.setUserName(userDto.getUserName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);

            idLocationDto.setId(user.getId());
            URI location = new URI("/user/" + user.getId());
            idLocationDto.setLocation(location);
        }
        return idLocationDto;
    }

    public User getUserById(String userId) {
        return userRepository.getById(userId);
    }

}
