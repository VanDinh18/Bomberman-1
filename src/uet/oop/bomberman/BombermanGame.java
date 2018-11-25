package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class BombermanGame {
	public static AudioLoop theme = new AudioLoop("Stage Theme.wav");

	public static Audio complete = new Audio("Stage Complete.wav");
	public static Audio game_over = new Audio("Game Over.wav");

	public static void main(String[] args) {
		theme.start();
		new Frame();
	}
}
