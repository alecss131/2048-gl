package ru.game.graph;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import ru.game.Background;
import ru.game.Button;
import ru.game.Cell;
import ru.game.Lock;

public class Render {
	private PipeLine pipeline;
	private SeparateShaderProgram vertex = new SeparateShaderProgram();
	private SeparateShaderProgram fragment = new SeparateShaderProgram();
	private SeparateShaderProgram geometry = new SeparateShaderProgram();
	private PipeLine pipeline1;
	private SeparateShaderProgram vertex1 = new SeparateShaderProgram();
	private SeparateShaderProgram fragment1 = new SeparateShaderProgram();
	private final Matrix4f proj;
	
	public Render() {
		proj = new Matrix4f();
	}
	
	public void init(Window window) {
		proj.ortho2D(0, window.getWidth(), window.getHeight(), 0);
		createCapabilities();
		try (MemoryStack stack = stackPush()) {
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			glfwGetFramebufferSize(window.getId(), width, height);
			glViewport(0, 0, width.get(0), height.get(0));
		}
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_MULTISAMPLE);
		glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEBUG_OUTPUT);
        glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DONT_CARE, 0, true);
        glDebugMessageCallback(new DebugCallBack(), 0);
        createShaders();
        pipeline = new PipeLine();
		pipeline.setFragmentStage(fragment.getId());
		pipeline.setVertexStage(vertex.getId());
		pipeline.setGeometryStage(geometry.getId());
		pipeline.validate();
		pipeline1 = new PipeLine();
		pipeline1.setFragmentStage(fragment1.getId());
		pipeline1.setVertexStage(vertex1.getId());
		pipeline1.validate();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void cleanUp() {
		vertex.cleanUp();
		fragment.cleanUp();
		geometry.cleanUp();
		pipeline.cleanUp();
		vertex1.cleanUp();
		fragment1.cleanUp();
		pipeline1.cleanUp();
	}
	
	public void render(Cell cell, Background bg) {
		clear();
		pipeline1.bind();
		vertex1.setUniform(0, proj);
		bg.render();
		pipeline1.unbind();
		pipeline.bind();
		geometry.setUniform(0, proj);
		cell.render();
		pipeline.unbind();
	}
	
	public void render(Button b) {
		pipeline1.bind();
		vertex1.setUniform(0, proj);
		b.render();
		pipeline1.unbind();
	}
	
	public void render(Lock item, boolean t) {
		pipeline1.bind();
		vertex1.setUniform(0, proj);
		item.render(t);
		pipeline1.unbind();
	}
	
	private void createShaders() {
		try {
			vertex.createVertexShader(IOUtils.resourceToString("/assets/shaders/tile.vs", StandardCharsets.UTF_8));
			fragment.createFragmentShader(IOUtils.resourceToString("/assets/shaders/tile.fs", StandardCharsets.UTF_8));
			geometry.createGeometryShader(IOUtils.resourceToString("/assets/shaders/tile.gs", StandardCharsets.UTF_8));
			vertex1.createVertexShader(IOUtils.resourceToString("/assets/shaders/shader.vs", StandardCharsets.UTF_8));
			fragment1.createFragmentShader(IOUtils.resourceToString("/assets/shaders/shader.fs", StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}