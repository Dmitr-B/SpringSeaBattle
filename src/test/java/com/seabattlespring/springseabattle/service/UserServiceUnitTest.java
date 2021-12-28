package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.IdLocationDto;
import com.seabattlespring.springseabattle.dto.UserDto;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    private static final String id = "61768b15d9e3d602d1a9934a";
    private static final String password = "unitPassword";
    private static final String userName = "unitUser";

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void createUser_whenHasNotContainInDB_thenSave() throws URISyntaxException {
        UserDto user = new UserDto(userName, password);
        IdLocationDto expectedIdLocationDto = new IdLocationDto();
        expectedIdLocationDto.setId(id);
        URI uri = new URI("/user/" + id);
        expectedIdLocationDto.setLocation(uri);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        IdLocationDto actualLocationDto = userService.createUser(user);
        actualLocationDto.setId(id);
        actualLocationDto.setLocation(uri);

        assertEquals(expectedIdLocationDto, actualLocationDto);
    }
}
