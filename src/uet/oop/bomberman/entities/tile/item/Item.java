package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Audio;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Tile {
	Audio power_up = new Audio("power_up.wav");
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
