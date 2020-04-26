package ru.game;

import java.awt.image.BufferedImage;

import ru.game.graph.Mesh;
import ru.game.graph.Texture;

public class Background {
	private Texture image;
	private Mesh mesh;
	
	public void init() {
		BufferedImage im = ResourceManager.getBackGroud();
		image = new Texture(im);
		float tex[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
		float vert[] = { 0.0f, 0.0f, 0.0f, im.getWidth(), 0.0f, 0.0f, 0.0f, im.getHeight(), 0.0f, im.getWidth(),
				im.getHeight(), 0.0f };
		mesh = new Mesh(vert, tex);
	}
	
	public void render() {
		image.bind();
		mesh.render();
		image.unbind();
	}
	
	public void cleanUp() {
		image.cleanUp();
		mesh.cleanUp();
	}
}