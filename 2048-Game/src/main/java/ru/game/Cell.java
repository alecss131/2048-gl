package ru.game;

import ru.game.graph.InstancedMesh;
import ru.game.graph.TextureArray;

public class Cell {
	private TextureArray tiles;
	private InstancedMesh mesh;
	
	public void init() {
		tiles = new TextureArray(ResourceManager.getNumberImages());
		mesh = new InstancedMesh(new int[] {5, 150, 100, 10}, 16);
	}
	
	public void updateCells(int in[]) {
		mesh.updateData(in);
	}
	
	public void render() {
		tiles.bind();
		mesh.render();
		tiles.unbind();
	}
	
	public void cleanUp() {
		tiles.cleanUp();
		mesh.cleanUp();
	}
}