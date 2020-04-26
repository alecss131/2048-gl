package ru.game;

import ru.game.graph.Mesh;
import ru.game.graph.Texture;

public class Button {
	private int x, y, w, h;
	private boolean mouseIn = false;
	private Texture onMouse;
	private Mesh mesh;
	
	public void init(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		onMouse = new Texture(ResourceManager.createImage(w, h, true));
		float tex[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
		float vert[] = { x, y, 0.0f, x+w, y, 0.0f, x, y+h, 0.0f, x+w, y+h, 0.0f };
		mesh = new Mesh(vert, tex);
	}
	
	public void render() {
		if (mouseIn) {
			onMouse.bind();
			mesh.render();
			onMouse.unbind();
		}
	}
	
	public void update(float[] mp) {
		if ((mp[0]>x && mp[0]<(x+w)) && (mp[1]>y && mp[1]<(y+h))) {
			mouseIn = true;
		} else {
			mouseIn = false;
		}
	}
	
	public boolean result(boolean active) {
		return mouseIn & active;
	}
	
	public void cleanUp() {
		onMouse.cleanUp();
		mesh.cleanUp();
	}
	
	public float getState() {
		return 0.0f;
	}
}