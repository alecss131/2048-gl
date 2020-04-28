package ru.game.graph;

import ru.game.Background;
import ru.game.Button;
import ru.game.Cell;
import ru.game.Field;
import ru.game.Lock;
import ru.game.Score;

public class GameLogic {
	private Field field = new Field();
	private Background bg = new Background();
	private Cell cell = new Cell();
	private Lock lock = new Lock();
	private Button btn1 = new Button();
	private Button btn2 = new Button();
	private Score score = new Score(375, 42, 11, 18);
	private Score bscore = new Score(270, 42, 11, 18);
	
	public void init() {
		field.init();
		cell.init();
		cell.updateCells(field.getField());
		bg.init();
		lock.init();
		btn1.init(320, 102, 100, 25);
		btn2.init(185, 420, 90, 25);
		score.init();
		bscore.init();
	}
	
	public void update (Window window, Render render) {
		if (!field.isGameOver() && !field.isWin()) {
			if (window.isKeySinglePressed(InputManager.getLeft())) {
				field.moveLeft();
			}
			if (window.isKeySinglePressed(InputManager.getRight())) {
				field.moveRight();
			}
			if (window.isKeySinglePressed(InputManager.getUp())) {
				field.moveUp();
			}
			if (window.isKeySinglePressed(InputManager.getDown())) {
				field.moveDown();
			}
		}
		if (field.isFieldChanged()) {
			cell.updateCells(field.getField());
			score.setScore(field.getScore());
		}
		btn1.update(window.getMousePos());
		render.render(cell, bg);
		if (field.isWin() || field.isGameOver()) {
			render.render(lock, field.isWin());
			btn2.update(window.getMousePos());
			if (field.isWin()) render.render(btn2);
		}
		render.render(btn1);
		render.render(score, bscore);
		if (btn1.result(true) && window.isMousePressed()) field.newGame();
		if (btn2.result(field.isWin()) && window.isMousePressed()) field.continueGame();
	}
	
	public void cleanUp() {
		cell.cleanUp();
		bg.cleanUp();
		lock.cleanUp();
		btn1.cleanUp();
		btn2.cleanUp();
		score.cleanUp();
		bscore.cleanUp();
	}
}