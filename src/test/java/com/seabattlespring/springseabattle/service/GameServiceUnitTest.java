package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.RedisGameRepository;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTest {

    private static final String gameId = "6176699182db2c5574e48917";
    private static final String userName1 = "unitName1";
    private static final String userName2 = "unitName2";
    private static final String userId1 = "61768b15d9e3d602d1a9934a";
    private static final String userId2 = "61768b15d9e3d602d1a9934b";

    @InjectMocks
    private GameService gameService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RedisGameRepository redisGameRepository;

    @Test
    void createGame_whenCreate_theOk() {
        String expected = "6176699182db2c5574e48917";
        User user = new User();
        Game game = new Game();
        Game updatedGame = new Game();
        updatedGame.setId(expected);
        game.setUser1(userId1);
        user.setId(userId1);
        user.setUserName(userName1);

        when(userRepository.getByUserName(userName1)).thenReturn(user);
        when(gameRepository.save(game)).thenReturn(updatedGame);

        String actual = gameService.createGame(userName1);

        assertEquals(expected, actual);
    }

    @Test
    void getGame_whenDBShouldThisGame() {
        Game expected = new Game();
        expected.setId(gameId);

        when(gameRepository.findGameById(gameId)).thenReturn(expected);

        Game actual = gameService.getGameById(gameId);

        assertEquals(expected, actual);
    }

    @Test
    void getGame_whenDoesNotShouldThisGame() {
        Game actual = gameService.getGameById(gameId);

        assertNull(actual);
    }

    @Test
    void joinGameById_whenExists_theOk() {
        Game game = new Game();
        game.setId(gameId);
        game.setUser1(userId1);
        User user = new User();
        user.setUserName(userName2);
        user.setId(userId2);

        when(gameRepository.findGameById(gameId)).thenReturn(game);
        when(userRepository.getByUserName(userName2)).thenReturn(user);

        gameService.joinGameById(gameId, userName2);

        verify(gameRepository).save(game);
    }

    @Test
    void joinGameById_whenDoesNotExists_thenThrow() {
        String expectedMessage = "This game is already taken";

        when(gameRepository.findGameById(gameId)).thenThrow(new IllegalArgumentException(expectedMessage));

        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> gameService.joinGameById(gameId, userName2));

        assertEquals(expectedMessage, actual.getMessage());
    }
}
