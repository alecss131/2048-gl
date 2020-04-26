package ru.game.graph;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {
	private float p[] = {0, 0};
	private int k, a;
	
	public MouseInput(long window) {
		glfwSetCursorPosCallback(window, (wnd, xpos, ypos) -> cpos(wnd, xpos, ypos));
		glfwSetMouseButtonCallback(window, (wnd, button, action, mods) -> mbut(wnd, button, action, mods));
	}
	
	private void mbut(long window, int button, int action, int mods) {
		k = button;
		a = action;
	}
	
	private void cpos(long window, double x, double y) {
		p[0] = (float)x;
		p[1] = (float)y;
	}
	
	public float[] getPos() {
		return p;
	}
	
	public boolean keyPressed() {
		if ((k == GLFW_MOUSE_BUTTON_LEFT) && (a == GLFW_PRESS)) {
			a = -1;
			return true;
		}
		return false;
	}
}