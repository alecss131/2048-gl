package ru.game.graph;

import static org.lwjgl.opengl.GL45.*;

public class InstancedMesh {
	private int vao;
	private int vbo;
	private int len;
	private final static int SIZE = 4;
	
	public InstancedMesh(int in[], int length) {
		len = length;
		vbo = glCreateBuffers();
		glNamedBufferStorage(vbo, 4 * SIZE + length * SIZE, GL_DYNAMIC_STORAGE_BIT);
		glNamedBufferSubData(vbo, 0, in);
		vao = glCreateVertexArrays();
		glEnableVertexArrayAttrib(vao, 0);
		glEnableVertexArrayAttrib(vao, 1);
		glVertexArrayAttribIFormat(vao, 0, 4, GL_INT, 0);
		glVertexArrayAttribIFormat(vao, 1, 1, GL_INT, 0);
		glVertexArrayVertexBuffer(vao, 0, vbo, 0, 0);
		glVertexArrayVertexBuffer(vao, 1, vbo, 4 * SIZE, SIZE);
		glVertexArrayAttribBinding(vao, 0, 0);
		glVertexArrayAttribBinding(vao, 1, 1);
		glVertexArrayBindingDivisor(vao, 1, 1);
	}
	
	public void updateData(int in[]) {
		glNamedBufferSubData(vbo, 4 * SIZE, in);
	}
	
	public void render() {
		glBindVertexArray(vao);
		glDrawArraysInstanced(GL_POINTS, 0, 1, len);
		glBindVertexArray(0);
	}
	
	public void cleanUp() {
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vao);
	}
}