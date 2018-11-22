package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class BombermanGame {
	public static Audio theme = new Audio("Stage Theme.wav");

	public static Audio complete = new Audio("Stage Complete.wav");
	public static Audio game_over = new Audio("Game Over.wav");

//	public static Audio audio = new Audio("bomberman_orchestra.wav");
	public static void main(String[] args) {
		new Frame();
	}
}
