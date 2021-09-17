package com.seabattlespring.springseabattle.game;

//import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import lombok.Data;
import org.springframework.stereotype.Component;

//@Data
//public class Player {
//    private String name;
//    private BattleMap battleMap;
//
//    public Player(String name) {
//        this.name = name;
//        this.battleMap = new BattleMap();
//    }
//
//    public boolean isEmptySinglePlace(int x, int y) {
//        return battleMap.getOnceShip(x, y) == null;
//    }
//
//    public boolean isValidSingleShip(int x, int y) {
//        return isEmptySinglePlace(x, y) && SingleDeckShip.getCounter() != 4;
//    }
//
//    public boolean isEmptyDoublePlace(int x, int y, int x1, int y1) {
//        return battleMap.getOnceShip(x, y) == null && battleMap.getOnceShip(x1, y1) == null;
//    }
//
//    public boolean isValidDoubleCoordinates(int x, int y, int x1, int y1) {
//
//        if (x1 != x && y1 !=y) {
//            return false;
//        }
//
//        return x1 == x + 1 || y1 == y + 1 || x1 == x - 1 || y1 == y -1; //
//    }
//
//    public boolean isValidDoubleShip(int x, int y, int x1, int y1) {
//        return true;
//    }
//}
