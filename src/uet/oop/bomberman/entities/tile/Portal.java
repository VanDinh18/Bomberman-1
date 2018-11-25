package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.complete;
import static uet.oop.bomberman.BombermanGame.theme;


public class Portal extends Tile {
	public Portal(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào

		if(e instanceof Bomber ){
			if(!Game.getBoard().detectNoEnemies()) {
				complete.start();
				Game.getBoard().nextLevel();
				return true;
			}
		}
		return false;
	}

}
