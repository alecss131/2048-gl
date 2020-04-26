package ru.game.graph;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
	private int vao;
	private int vbo;
	private int len;
	private final static long SIZE = 4;
	
	public Mesh(float vertex[], float texcolor[]) {
		len = vertex.length / 3;
		int tcSize =  texcolor.length / len;
		vbo = glCreateBuffers();
		glNamedBufferStorage(vbo, (vertex.length + texcolor.length) * SIZE, GL_DYNAMIC_STORAGE_BIT);
		glNamedBufferSubData(vbo, 0, vertex);
		glNamedBufferSubData(vbo, vertex.length * SIZE, texcolor);
		vao = glCreateVertexArrays();
		glEnableVertexArrayAttrib(vao, 0);
		glEnableVertexArrayAttrib(vao, 1);
		glVertexArrayAttribFormat(vao, 0, 3, GL_FLOAT, false, 0);
		glVertexArrayAttribFormat(vao, 1, tcSize, GL_FLOAT, false, 0);
		glVertexArrayVertexBuffer(vao, 0, vbo, 0, 3 * (int) SIZE);
		glVertexArrayVertexBuffer(vao, 1, vbo, vertex.length * SIZE, tcSize * (int) SIZE);
		glVertexArrayAttribBinding(vao, 0, 0);
		glVertexArrayAttribBinding(vao, 1, 1);
	}
	
	public void render() {
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, len);
		glBindVertexArray(0);
	}
	
	public void cleanUp() {
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vao);
	}
}