package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import java.util.List;
import java.util.Random;

public class BomberAI {

    public static int calculateDirection(List<Bomb> bombs, Bomber bomber) {
        Random random = new Random();
        // TODO: cài đặt thuật toán tìm đường đi
        if(!Game.getBoard().detectNoEnemies()) {
            for (int i = 1; i < 3; i++) {

                Character character = Game.getBoard().getCharacterAtExcluding(bomber.getXTile() + i, bomber.getYTile(), bomber);
                if (character != null) {
                    System.out.println("llllll");
                    return 3;
                }
            }
            for (int i = 1; i < 3; i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(bomber.getXTile() - i, bomber.getYTile(), bomber);
                if (character != null) {
                    return 1;
                }
            }
            for (int i = 1; i < 3; i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(bomber.getXTile(), bomber.getYTile() + i, bomber);
                if (character != null) {
                    return 0;
                }
            }
            for (int i = 1; i < 3; i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(bomber.getXTile(), bomber.getYTile() - i, bomber);
                if (character != null) {
                    return 2;
                }
            }
        }
        if( Game.getBombRate() >0 ){
            return random.nextInt(4);
        }

        else {
            for (int i = 0; i < bombs.size() ; i++) {
                int dX = Math.abs(bomber.getXTile() - (int) bombs.get(i).getX());
                int dY = Math.abs(bomber.getYTile() - (int) bombs.get(i).getY());
                System.out.println(dX + " " + dY);
                if (dX <= Game.getBombRadius() && dY <= Game.getBombRadius()) {
                    if(bomber.canMove(0,-5)) return 0;
                    else if(bomber.canMove(5,0)) return 1;
                    else if(bomber.canMove(0,5)) return 2;
                    else if(bomber.canMove(-5,0)) return 3;
                }
                else {
                    if(bomber.getXTile() > (int) bombs.get(i).getX()){
                        return 1;
                    }
                    if(bomber.getXTile() < (int) bombs.get(i).getX()){
                        return 3;
                    }
                    if(bomber.getYTile() > (int) bombs.get(i).getY()){
                        return 2;
                    }
                    if(bomber.getXTile() < (int) bombs.get(i).getX()){
                        return 0;
                    }
                }
            }
        }
        return 4;
    }
}