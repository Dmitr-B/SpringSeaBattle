package com.seabattlespring.springseabattle.repository.domain;

public enum State {
    //todo ожидание хода игрока 1 и игрока 2. Коли гравці заповнили поля то рандомно визначить
    ARRANGEMENT, PLAYER1TURN, PLAYER2TURN, FIGHT, OVER
}
