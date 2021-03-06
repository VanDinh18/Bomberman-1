package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Audio;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if(e instanceof Bomber){
			power_up.start();
			if(this.isRemoved()) return true;
			remove();
			Game.addBomberSpeed(0.5);
		}
		return false;
	}
}
