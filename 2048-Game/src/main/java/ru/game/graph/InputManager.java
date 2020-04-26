package ru.game.graph;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
	private static int up = GLFW_KEY_UP;
	private static int down = GLFW_KEY_DOWN;
	private static int left = GLFW_KEY_LEFT;
	private static int right = GLFW_KEY_RIGHT;
	private static int confirm = GLFW_KEY_ENTER;
	private static int exit = GLFW_KEY_ESCAPE;
	private static int jump = GLFW_KEY_RIGHT_SHIFT;
	private static int fire = GLFW_KEY_RIGHT_CONTROL;
	private static int delete = GLFW_KEY_DELETE;
	private static int hide = GLFW_KEY_RIGHT_ALT;
	private static int mode = GLFW_KEY_SPACE;
	
	public static int getUp() {
		return up;
	}

	public static int getDown() {
		return down;
	}

	public static int getLeft() {
		return left;
	}

	public static int getRight() {
		return right;
	}

	public static int getConfirm() {
		return confirm;
	}

	public static int getExit() {
		return exit;
	}

	public static int getJump() {
		return jump;
	}

	public static int getFire() {
		return fire;
	}
	
	public static int getDelete() {
		return delete;
	}
	
	public static int getHide() {
		return hide;
	}
	
	public static int getMode() {
		return mode;
	}
}