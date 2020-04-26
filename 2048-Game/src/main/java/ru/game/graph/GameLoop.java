package ru.game.graph;

public class GameLoop implements Runnable {

	private int TARGET_FPS = 60;
	private final Window window;
	private final Timer timer;
	private final Render render;
	private boolean vSync;
	private final GameLogic game;

	public GameLoop(String windowTitle, int width, int height, boolean vSync, boolean fullScreen) {
		game = new GameLogic();
		render = new Render();
		this.vSync = vSync;
		window = new Window(windowTitle, width, height, vSync, fullScreen);
		timer = new Timer();
	}

	@Override
	public void run() {
		try {
			init();
			gameLoop();
			cleanup();
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	private void init() {
		window.init();
		render.init(window);
		timer.init();
		game.init();
	}

	private void gameLoop() {
		window.show();
		while (!window.windowShouldClose()) {
			game.update(window, render);
			window.update();
			if (!vSync) {
				sync();
			}
		}
	}

	private void cleanup() {
		render.cleanUp();
		window.cleanUp();
		game.cleanUp();
	}

	private void sync() {
		int loopSlot = 1000 / TARGET_FPS;
		long endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}