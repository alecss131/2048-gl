package ru.game;

import ru.game.graph.InstancedMesh;
import ru.game.graph.Texture;

public class Score {
	private Texture numbers;
	private InstancedMesh mesh;
	int x, y, w, h;
	
	public Score(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void init() {
		mesh = new InstancedMesh(new int[] {x, y, w, h}, 6);
		numbers = new Texture(ResourceManager.getNumbers());
		setScore(0);
	}
	
	public void setScore(int score) {
		mesh.updateData(Integer.toString(score).chars().map(c -> c - '0').toArray());
	}
	
	public void render() {
		numbers.bind();
		mesh.render();
		numbers.unbind();
	}
	
	public void cleanUp() {
		mesh.cleanUp();
		numbers.cleanUp();
	}
}