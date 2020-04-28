package ru.game;

import java.util.Arrays;
import java.util.Random;

public class Field {
	private int[] field;
	private int[] field2 = new int[16];
	private boolean win;
	private boolean cont;
	private boolean over;
	private boolean changed = false;;
	private int score;
	
	public void init() {
		newGame();
	}
	
	public boolean isFieldChanged() {
		if (changed) {
			changed = false;
			return true;
		}
		return false;
	}
	
	public int[] getField() {
		return field;
	}
	
	public void moveLeft() {
		field2 = Arrays.copyOf(field, field.length);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=0; k<3; k++) {
					if (field[4*i+k]==0) {
						field[4*i+k] = field[4*i+k+1];
						field[4*i+k+1] = 0;
					}
				}
			}
			for (int j=1; j<4; j++) {
				if (field[4*i+j-1]==field[4*i+j] && field[4*i+j] !=0) {
					field[4*i+j-1] += 1;
					score += Math.pow(2, field[4*i+j-1]);
					field[4*i+j] = 0;
				}
			}
			for (int j=0; j<4; j++) {
				for (int k=0; k<3; k++) {
					if (field[4*i+k]==0) {
						field[4*i+k] = field[4*i+k+1];
						field[4*i+k+1] = 0;
					}
				}
			}
		}
		endMove();
	}
	
	public void moveRight() {
		field2 = Arrays.copyOf(field, field.length);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=3; k>0; k--) {
					if (field[4*i+k]==0) {
						field[4*i+k] = field[4*i+k-1];
						field[4*i+k-1] = 0;
					}
				}
			}
			for (int j=3; j>0; j--) {
				if (field[4*i+j-1]==field[4*i+j] && field[4*i+j]!=0) {
					field[4*i+j] += 1;
					score += Math.pow(2, field[4*i+j]);
					field[4*i+j-1] = 0;
				}
			}
			for (int j=0; j<4; j++) {
				for (int k=3; k>0; k--) {
					if (field[4*i+k]==0) {
						field[4*i+k] = field[4*i+k-1];
						field[4*i+k-1] = 0;
					}
				}
			}
		}
		endMove();
	}
	
	public void moveUp() {
		field2 = Arrays.copyOf(field, field.length);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=0; k<3; k++) {
					if (field[4*k+i]==0) {
						field[4*k+i] = field[4*(k+1)+i];
						field[4*(k+1)+i] = 0;
					}
				}
			}
			for (int j=1; j<4; j++) {
				if (field[4*(j-1)+i]==field[4*j+i] && field[4*j+i]!=0) {
					field[4*(j-1)+i] += 1;
					score += Math.pow(2, field[4*(j-1)+i]);
					field[4*j+i] = 0;
				}
			}
			for (int j=0; j<4; j++) {
				for (int k=0; k<3; k++) {
					if (field[4*k+i]==0) {
						field[4*k+i] = field[4*(k+1)+i];
						field[4*(k+1)+i] = 0;
					}
				}
			}
		}
		endMove();
	}
	
	public void moveDown() {
		field2 = Arrays.copyOf(field, field.length);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=3; k>0; k--) {
					if (field[4*k+i]==0) {
						field[4*k+i] = field[4*(k-1)+i];
						field[4*(k-1)+i] = 0;
					}
				}
			}
			for (int j=3; j>0; j--) {
				if (field[4*(j-1)+i]==field[4*j+i] && field[4*j+i]!=0) {
					field[4*j+i] += 1;
					score += Math.pow(2, field[4*j+i]);
					field[4*(j-1)+i] = 0;
				}
			}
			for (int j=0; j<4; j++) {
				for (int k=3; k>0; k--) {
					if (field[4*k+i]==0) {
						field[4*k+i] = field[4*(k-1)+i];
						field[4*(k-1)+i] = 0;
					}
				}
			}
		}
		endMove();
	}
	
	public boolean isGameOver() {
		return over;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isWin() {
		return win;
	}
	
	public void newGame() {
		score = 0;
		field = new int[16];
		field2 = new int[16];
		win = false;
		over = false;
		cont = false;
		changed = true;
		addNumber();
		addNumber();
	}
	
	private void endMove() {
		if (isChanged()) {
			checkWin();
			addNumber();
			checkGameOver();
			changed = true;
		}
	}
	
	public void continueGame() {
		win = false;
		addNumber();
		checkGameOver();
	}
	
	private boolean hasEmptyCells() {
		for (int i = 0; i < field.length; i++) {
			if (field[i] == 0) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isChanged() {
		for (int i = 0; i < field.length; i++) {
			if (field[i] != field2[i]) {
				return true;
			}
		}
		return false;
	}
	
	private void checkWin() {
		if (!cont) {
			for (int i = 0; i < field.length; i++) {
				if (field[i] == 11) {
					win = true;
					cont = true;
				}
			}
		}
	}
	
	private void checkGameOver() {
		if (!hasEmptyCells()) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					if ((field[4*i+j] == field[4*i+j + 1]) || (field[4*j+i] == field[4*(j + 1)+i])) {
						return;
					}
				}
			}
			over = true;
		}
	}
	
	private void addNumber() {
		if (!over && hasEmptyCells() && !win) {
			int number = getNumber();
			int x;
			do {
			x = new Random().nextInt(field.length);
			if (field[x] == 0) {
				field[x] = number;
				break;
			}
			} while (field[x] != 0);
		}
	}
	
	private int getNumber() {
		switch (new Random().nextInt(100)%4) {
		case 0 : {
			return 1;
		}
		case 1 : { 
			return 2;
		}
		case 2 : {
			return 1;
		}
		case 3 : {
			return 1;
		}
		default :
			return 3;
		}
	}
}