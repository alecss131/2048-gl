package ru.game;

import java.awt.image.BufferedImage;
import ru.game.graph.Mesh;
import ru.game.graph.Texture;

public class Lock {
	private Texture win;
	private Texture loose;
	private Mesh mesh;
	
	public void init() {
		BufferedImage im = ResourceManager.getWinImage();
		win = new Texture(im);
		loose = new Texture(ResourceManager.getLooseImage());
		float tex[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
		float vert[] = { 5.0f, 150.0f, 0.0f, 5.0f+im.getWidth(), 150.0f, 0.0f, 5.0f, 150.0f+im.getHeight(), 0.0f, 5.0f+im.getWidth(),
				150.0f+im.getHeight(), 0.0f };
		mesh = new Mesh(vert, tex);
	}
	
	public void render(boolean type) {
		if (type) {
			win.bind();
		} else {
			loose.bind();
		}
		mesh.render();
		win.unbind();
	}
	
	public void cleanUp() {
		win.cleanUp();
		loose.cleanUp();
		mesh.cleanUp();
	}
}