package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.game.validator.ship.exception.*;
import com.seabattlespring.springseabattle.game.validator.shot.ShotValidator;
import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.RedisGameRepository;
import com.seabattlespring.springseabattle.repository.StatRepository;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Mock
    private ShotValidator shotValidator;
    @Mock
    private StatRepository statRepository;

    @Test
    void createGame_whenCreate_theOk() {
        String expected = "6176699182db2c5574e48917";
        User testUser = new User();
        Game testGame = new Game();
        Game testUpdatedGame = new Game();
        testUpdatedGame.setId(expected);
        testGame.setUser1(userId1);
        testUser.setId(userId1);
        testUser.setUserName(userName1);

        when(userRepository.getByUserName(userName1)).thenReturn(testUser);
        when(gameRepository.save(testGame)).thenReturn(testUpdatedGame);

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
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        User testUser = new User();
        testUser.setUserName(userName2);
        testUser.setId(userId2);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName2)).thenReturn(testUser);

        gameService.joinGameById(gameId, userName2);

        verify(gameRepository).save(testGame);
    }

    @Test
    void joinGameById_whenDoesNotExists_thenThrow() {
        String expectedMessage = "This game is already taken";

        when(gameRepository.findGameById(gameId)).thenThrow(new IllegalArgumentException(expectedMessage));

        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> gameService.joinGameById(gameId, userName2));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test()
    void joinToRandomGame_whenExists_theOk() {
        Game testGame = new Game();
        Date testDate = new Date();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        User testUser = new User();
        testUser.setUserName(userName2);
        testUser.setId(userId2);

        when(redisGameRepository.getAvailableGame("available_games")).thenReturn("6176699182db2c5574e48917::" + testDate.getTime() + 60000);
        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName2)).thenReturn(testUser);

        gameService.joinToRandomGame(testUser.getUserName());

        verify(gameRepository).save(testGame);
    }

    @Test
    void joinToRandomGame_whenDoesNotExists_thenThrow() {
        String expectedMessage = "There are no free games now";
        User testUser = new User();
        testUser.setId(userId2);
        testUser.setUserName(userName2);

        when(redisGameRepository.getAvailableGame("available_games")).thenReturn(null);
        when(userRepository.getByUserName(userName2)).thenReturn(testUser);

        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> gameService.joinToRandomGame(userName2));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenExists_theOk() throws OneStraightLineException, PlayerException, CellEmptyException, NumberOfCoordinatesException, NearbyCoordinatesException, NumberOfValidShipException {
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates = new Coordinates(0,0);

        Cell testCell = new Cell();
        testCell.setCellState(CellState.SHIP);
        testCell.setCoordinates(testCoordinates);
        testCells.add(testCell);

        Ship testShip = new Ship(ShipType.SINGLE, testCells);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip);
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowNumberOfCoordinatesException() {
        String expectedMessage = "Mismatch in the number of coordinates with the decks";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates = new Coordinates(0,0);

        Cell testCell = new Cell();
        testCell.setCellState(CellState.SHIP);
        testCell.setCoordinates(testCoordinates);
        testCells.add(testCell);

        Ship testShip = new Ship(ShipType.DOUBLE, testCells);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        NumberOfCoordinatesException actual = assertThrows(NumberOfCoordinatesException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowOneStraightLineException() {
        String expectedMessage = "The ship must be horizontal or vertical";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(1,1);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);

        Ship testShip = new Ship(ShipType.DOUBLE, testCells);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        OneStraightLineException actual = assertThrows(OneStraightLineException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowNearbyCoordinatesException() {
        String expectedMessage = "The deck of the ship is not nearby";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(0,2);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);

        Ship testShip = new Ship(ShipType.DOUBLE, testCells);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        NearbyCoordinatesException actual = assertThrows(NearbyCoordinatesException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowNumberOfValidShipException() {
        String expectedMessage = "The maximum number of ships of a certain type has been reached";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(0,1);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);

        Ship testShip = new Ship(ShipType.DOUBLE, testCells);

        ShipDto shipDto = new ShipDto();
        shipDto.setShipType(ShipType.DOUBLE);
        shipDto.setCells(testCells);

        testGame.getFightField1().getShips().add(shipDto);
        testGame.getFightField1().getShips().add(shipDto);
        testGame.getFightField1().getShips().add(shipDto);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        NumberOfValidShipException actual = assertThrows(NumberOfValidShipException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowCellEmptyException() {
        String expectedMessage = "The cell is already busy";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(0,1);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);

        Ship testShip = new Ship(ShipType.DOUBLE, testCells);

        ShipDto shipDto = new ShipDto();
        shipDto.setShipType(ShipType.DOUBLE);
        shipDto.setCells(testCells);

        testGame.getFightField1().getCells().get(0).get(0).setCellState(CellState.SHIP);
        testGame.getFightField1().getCells().get(0).get(0).setCoordinates(testCoordinates1);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        CellEmptyException actual = assertThrows(CellEmptyException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void addShip_whenDoesNotExists_thenThrowPlayerException() {
        String expectedMessage = "User not found in a game";
        Game testGame = new Game();
        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2("61768b15d9e3d602d1a9934c");

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId2);

        List<Cell> testCells = new ArrayList<>();

        Coordinates testCoordinates = new Coordinates(0,0);

        Cell testCell = new Cell();
        testCell.setCellState(CellState.SHIP);
        testCell.setCoordinates(testCoordinates);
        testCells.add(testCell);

        Ship testShip = new Ship(ShipType.SINGLE, testCells);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);

        PlayerException actual = assertThrows(PlayerException.class,
                () -> gameService.addShip(gameId, userName1, FightField.Owner.PLAYER1, testShip));

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void shot_whenExists_thenReturnPastState() throws ShotException, PlayerException {
        CellState expected = CellState.PAST;

        Game testGame = new Game();

        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);
        testGame.setState(State.PLAYER1TURN);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();
        List<ShipDto> testShips = new ArrayList<>();

        Coordinates testCoordinates = new Coordinates(0,0);

        Cell testCell = new Cell();
        testCell.setCellState(CellState.SHIP);
        testCell.setCoordinates(testCoordinates);
        testCells.add(testCell);

        ShipDto testShip = new ShipDto();
        testShip.setShipType(ShipType.SINGLE);
        testShip.setCells(testCells);
        testShips.add(testShip);

        testGame.getFightField1().setShips(testShips);
        testGame.getFightField2().setShips(testShips);

        Shot testShot = new Shot(testCoordinates);

        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);
        when(shotValidator.valid(testGame, testGame.getFightField2())).thenReturn(true);

        CellState actual = gameService.shot(gameId, userName1, FightField.Owner.PLAYER1, testShot);

        assertEquals(expected, actual);
    }

    @Test
    void shot_whenExists_thenReturnKnockedState() throws ShotException, PlayerException {
        CellState expected = CellState.KNOCKED;

        Game testGame = new Game();

        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);
        testGame.setState(State.PLAYER1TURN);

        User testUser = new User();
        testUser.setUserName(userName1);
        testUser.setId(userId1);

        List<Cell> testCells = new ArrayList<>();
        List<ShipDto> testShips = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(0,1);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);


        ShipDto testShip = new ShipDto();
        testShip.setShipType(ShipType.DOUBLE);
        testShip.setCells(testCells);


        testShips.add(testShip);

        testGame.getFightField1().setShips(testShips);
        testGame.getFightField2().setShips(testShips);
        testGame.getFightField2().getCells().get(0).get(0).setCellState(CellState.SHIP);
        testGame.getFightField2().getCells().get(0).get(0).setCoordinates(testCoordinates1);
        testGame.getFightField2().getCells().get(0).get(1).setCellState(CellState.SHIP);
        testGame.getFightField2().getCells().get(0).get(1).setCoordinates(testCoordinates2);

        Shot testShot = new Shot(testCoordinates1);


        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser);
        when(shotValidator.valid(testGame, testGame.getFightField2())).thenReturn(true);

        CellState actual = gameService.shot(gameId, userName1, FightField.Owner.PLAYER1, testShot);

        assertEquals(expected, actual);
    }

    @Test
    void shot_whenExists_thenReturnSunkState() throws ShotException, PlayerException {
        CellState expected = CellState.SUNK;

        Game testGame = new Game();

        testGame.setId(gameId);
        testGame.setUser1(userId1);
        testGame.setUser2(userId2);
        testGame.setState(State.PLAYER1TURN);

        User testUser1 = new User();
        testUser1.setUserName(userName1);
        testUser1.setId(userId1);

        User testUser2 = new User();
        testUser1.setUserName(userName2);
        testUser1.setId(userId2);

        List<Cell> testCells = new ArrayList<>();
        List<ShipDto> testShips = new ArrayList<>();

        Coordinates testCoordinates1 = new Coordinates(0,0);
        Coordinates testCoordinates2 = new Coordinates(0,2);

        Cell testCell1 = new Cell();
        testCell1.setCellState(CellState.SHIP);
        testCell1.setCoordinates(testCoordinates1);
        testCells.add(testCell1);

        Cell testCell2 = new Cell();
        testCell2.setCellState(CellState.SHIP);
        testCell2.setCoordinates(testCoordinates2);
        testCells.add(testCell2);

        ShipDto testShip1 = new ShipDto();
        testShip1.setShipType(ShipType.SINGLE);
        testShip1.setCells(testCells);

        ShipDto testShip2 = new ShipDto();
        testShip2.setShipType(ShipType.SINGLE);
        testShip2.setCells(testCells);

        testShips.add(testShip1);
        testShips.add(testShip2);

        testGame.getFightField1().setShips(testShips);
        testGame.getFightField2().setShips(testShips);
        testGame.getFightField2().getCells().get(0).get(0).setCellState(CellState.SHIP);
        testGame.getFightField2().getCells().get(0).get(0).setCoordinates(testCoordinates1);
        testGame.getFightField2().getCells().get(0).get(2).setCellState(CellState.SHIP);
        testGame.getFightField2().getCells().get(0).get(2).setCoordinates(testCoordinates2);

        System.out.println(testGame);

        Shot testShot = new Shot(testCoordinates1);


        when(gameRepository.findGameById(gameId)).thenReturn(testGame);
        when(userRepository.getByUserName(userName1)).thenReturn(testUser1);
        //when(userRepository.getByUserName(userName2)).thenReturn(testUser2);
        when(shotValidator.valid(testGame, testGame.getFightField2())).thenReturn(true);

        CellState actual = gameService.shot(gameId, userName1, FightField.Owner.PLAYER1, testShot);

        assertEquals(expected, actual);
    }


}
