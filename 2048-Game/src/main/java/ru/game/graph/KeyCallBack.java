package ru.game.graph;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import static org.lwjgl.glfw.GLFW.*;

public class KeyCallBack implements GLFWKeyCallbackI {
	private int key;
	private int action;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		this.key = key;
		this.action = action;
	}
	
	public boolean isKeyPressed(int keyCode) {
		if ((key == keyCode) && (action == GLFW_PRESS)) {
			action = -1;
			return true;
		}
		return false;
	}
	
	public boolean isKeyReleased() {
		return (action == GLFW_RELEASE);
	}
}