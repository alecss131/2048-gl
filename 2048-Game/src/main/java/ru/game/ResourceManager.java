package ru.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ResourceManager {
	private static int length;
	private static BufferedImage[] images;
	private static BufferedImage field;
	private static BufferedImage backGround;
	private static BufferedImage icon;
	private static BufferedImage win;
	private static BufferedImage loose;
	private static int width;
	private static int height;
	private static int numSize = 100;
	private static int space = 10;
	private static int rad = 5;
	private static Color colors[] = {
			new Color(206, 194, 181, 255),
			new Color(239, 229, 219, 255),
			new Color(238, 225, 201, 255),
			new Color(242, 179, 122, 255),
			new Color(245, 150, 99, 255),
			new Color(245, 127, 96, 255),
			new Color(246, 94, 56, 255),
			new Color(238, 208, 115, 255),
			new Color(238, 205, 97, 255),
			new Color(238, 201, 79, 255),
			new Color(238, 198, 61, 255),
			new Color(237, 197, 50, 255)};
	private static int fontSize[] = {0, 50, 50, 50, 50, 50, 50, 45, 45, 45, 35, 35, 35, 30};
	private static Color fontcolors[] = {new Color(120, 110, 101, 255), new Color(249, 246, 242, 255)};
	private static BufferedImage out;
	
	public static void init() {
		init(4, 4, 8192);
	}
	
	public static void init(int maxNumber) {
		init(4, 4, maxNumber);
	}
	
	public static void init(int height, int width) {
		init(height, width, 8192);
	}
	
	public static void init(int height, int width, int maxNumber) {
		ResourceManager.height = height;
		ResourceManager.width = width;
		length = log2(maxNumber);
		images = new BufferedImage[length+1];
		createNubersImages();
		createBackGround();
		createIcon();
		createwinloose();
	}
	
	
	private static int log2(int in) {
		return Integer.toBinaryString(in).length()-1;
	}
	
	public static BufferedImage getFieldImage() {
		return field;
	}
	
	public static BufferedImage getWinImage() {
		return win;
	}
	
	public static BufferedImage getLooseImage() {
		return loose;
	}
	
	public static BufferedImage getBackGroud() {
		return backGround;
	}
	
	public static BufferedImage getIcon() {
		return icon;
	}
	
	public static BufferedImage[] getNumberImages() {
		return images;
	}
	
	private static void createNubersImages() {
		String text;
		int w;
		int h;
		int a;
		for (int i = 0; i < length + 1; i++) {
			images[i] = new BufferedImage(numSize, numSize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) images[i].getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(255, 255, 255, 0));
			g2d.fillRect(0, 0, numSize, numSize);
			if (i >= colors.length) {
				g2d.setColor(
						new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256), 255));
			} else {
				g2d.setColor(colors[i]);
			}
			g2d.fillRoundRect(0, 0, numSize, numSize, rad, rad);
			if (i < 3) {
				g2d.setColor(fontcolors[0]);
			} else {
				g2d.setColor(fontcolors[1]);
			}
			if (i > 0) {
				g2d.setFont(new Font("Arial", Font.BOLD, fontSize[i]));
				text = ((int) Math.pow(2, i)) + "";
				a = g2d.getFontMetrics().getAscent();
				w = g2d.getFontMetrics().stringWidth(text);
				h = g2d.getFontMetrics().getHeight();
				g2d.drawString(text, (numSize - w) / 2, (numSize - h) / 2 + a);
			}
		}
	}
	
	public static BufferedImage createImage(int w, int h, boolean type) {
		out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) out.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, w, h);
		if (type) {
			g2d.setColor(new Color(184, 163, 142, 120));
			g2d.fillRoundRect(0, 0, w, h, 5, 5);
		} else {
			g2d.setColor(new Color(104, 83, 62, 120));
			g2d.fillRoundRect(0, 0, w, h, 5, 5);
		}
		return out;
	}
	
	private static void createwinloose() {
		int a, tw, th;
		int w = numSize * width + (width + 1) * space;
		int h = numSize * height + (height + 1) * space;
		win = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) win.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(new Color(235, 195, 20, 120));
		g2d.fillRoundRect(0, 0, w, h, 2*rad, 2*rad);
		g2d.setColor(new Color(120, 110, 101, 255));
		g2d.setFont(new Font("Arial", Font.BOLD, 50));
		String text = "You Win";
		a = g2d.getFontMetrics().getAscent();
		tw = g2d.getFontMetrics().stringWidth(text);
		th = g2d.getFontMetrics().getHeight();
		g2d.drawString(text, (w-tw)/2, (h-th)/2+a);
		g2d.setColor(new Color(144, 123, 102, 255));
		g2d.fillRoundRect(180, 270, 90, 25, 5, 5);
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.setColor(new Color(255, 255, 255, 255));
		text = "Continue";
		a = g2d.getFontMetrics().getAscent();
		tw = g2d.getFontMetrics().stringWidth(text);
		th = g2d.getFontMetrics().getHeight();
		g2d.drawString(text, 180+(90-tw)/2, 270+(25-th)/2+a);
		loose = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) loose.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(new Color(235, 195, 20, 120));
		g2d.fillRoundRect(0, 0, w, h, 2*rad, 2*rad);
		g2d.setColor(new Color(240, 90, 50, 255));
		g2d.setFont(new Font("Arial", Font.BOLD, 50));
		text = "Game Over";
		a = g2d.getFontMetrics().getAscent();
		tw = g2d.getFontMetrics().stringWidth(text);
		th = g2d.getFontMetrics().getHeight();
		g2d.drawString(text, (w-tw)/2, (h-th)/2+a);
	}
	
	private static void createBackGround() {
		int w = 460;
		int h = 605;
		//int r = 15;
		backGround = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) backGround.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(new Color(250, 248, 255, 250));
		//g2d.fillRoundRect(0, 0, w, h, r, r);
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(new Color(120, 110, 101, 250));
		g2d.setFont(new Font("Arial", Font.BOLD, 80));
		g2d.drawString("2048", 20, 80);
		g2d.setFont(new Font("Arial", Font.TRUETYPE_FONT, 15));
		String text = "Join the numbers and get to the";
		g2d.drawString(text, 20, 120);
		int tw = g2d.getFontMetrics().stringWidth(text);
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.drawString("2048 tile!", tw+23, 120);
		int fieldWidth = numSize * width + (width + 1) * space;
		int fieldHeight = numSize * height + (height + 1) * space;
		g2d.setColor(new Color(188, 175, 161, 255));
		g2d.fillRoundRect(5, 150, fieldWidth, fieldHeight, 2*rad, 2*rad);
		g2d.setColor(new Color(188, 175, 161, 250)); //score
		g2d.fillRoundRect(355, 20, 100, 50, 5, 5); 
		g2d.fillRoundRect(250, 20, 100, 50, 5, 5);
		g2d.setColor(new Color(255, 255, 255, 255));
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		w = g2d.getFontMetrics().stringWidth("BEST");
		g2d.drawString("BEST", 355+(100-w)/2, 35);
		w = g2d.getFontMetrics().stringWidth("SCORE");
		g2d.drawString("SCORE", 250+(100-w)/2, 35);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		w = g2d.getFontMetrics().stringWidth("0");
		g2d.drawString("0", 355+(100-w)/2, 60);
		w = g2d.getFontMetrics().stringWidth("0");
		g2d.drawString("0", 250+(100-w)/2, 60);
		g2d.setColor(new Color(144, 123, 102, 255));
		g2d.fillRoundRect(320, 102, 100, 25, 5, 5); //new game
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.setColor(Color.white);
		int a = g2d.getFontMetrics().getAscent();
		w = g2d.getFontMetrics().stringWidth("New game");
		h = g2d.getFontMetrics().getHeight();
		g2d.drawString("New game", 320+(100-w)/2, 102+(25-h)/2+a);
	}
	
	private static void createIcon() {
		int size = 128;
		icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) icon.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(237, 197, 0));
		g2d.fillRect(0, 0, size, size);
		g2d.setColor(new Color(255, 255, 255));
		g2d.setFont(new Font("Arial", Font.BOLD, 44));
		String text = "2048";
		int a = g2d.getFontMetrics().getAscent();
		int w = g2d.getFontMetrics().stringWidth(text);
		int h = g2d.getFontMetrics().getHeight();
		g2d.drawString(text, (size-w)/2, (size-h)/2+a);
	}
}