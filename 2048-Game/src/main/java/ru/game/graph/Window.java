package ru.game.graph;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import ru.game.ResourceManager;

public class Window {
	private String title;
	private int width;
	private int height;
	private boolean vSync;
	private long window;
	private final KeyCallBack keyCallback;
	private boolean fullScreen;
	private long monitor = NULL;
	private MouseInput mouseIn;

	public Window(String title, int width, int height, boolean vSync, boolean fullScreen) {
		keyCallback = new KeyCallBack();
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		this.fullScreen = fullScreen;
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE); 
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_SAMPLES, 4);
		//glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);
		if (fullScreen) {
			monitor = glfwGetPrimaryMonitor();
		}
		window = glfwCreateWindow(width, height, title, monitor, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		setIcon();
		setCenterWindowPos();
		glfwSetKeyCallback(window, keyCallback);
		glfwMakeContextCurrent(window);
		if (vSync) {
			glfwSwapInterval(1);
		}
		mouseIn = new MouseInput(window);
	}

	public boolean isKeySinglePressed(int keyCode) {
		return keyCallback.isKeyPressed(keyCode);
	}
	
	public boolean isMousePressed() {
		return mouseIn.keyPressed();
	}
	
	public void setDecorated(boolean decorated) {
		glfwSetWindowAttrib(window, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public void cleanUp() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(window);
	}

	public void setWindowShouldClose() {
		glfwSetWindowShouldClose(window, true);
	}

	public void show() {
		glfwShowWindow(window);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public long getId() {
		return window;
	}
	
	public float[] getMousePos() {
		return mouseIn.getPos();
	}

	private void setIcon() {
		try (MemoryStack stack = stackPush(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			ImageIO.write(ResourceManager.getIcon(), "png", baos);
			baos.flush();
			byte[] data32 = baos.toByteArray();
			ByteBuffer icon32 = stack.malloc(data32.length);
			icon32.put(data32).flip();
			ByteBuffer cur = stbi_load_from_memory(icon32, w, h, c, STBI_rgb_alpha);
			GLFWImage.Buffer icons = GLFWImage.mallocStack(1, stack);
			icons.get(0).width(w.get(0)).height(h.get(0)).pixels(cur);
			glfwSetWindowIcon(window, icons);
			stbi_image_free(cur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setCenterWindowPos() {
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
	}
}