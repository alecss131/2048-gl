package ru.game.graph;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL45.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.system.MemoryStack;

public class Texture {
	private int width;
	private int height;
	private int id;
	
	public Texture(BufferedImage image) {
		try (MemoryStack stack = stackPush(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			ImageIO.write(image, "png", baos);
			baos.flush();
			byte[] data = baos.toByteArray();
			ByteBuffer buf = memAlloc(data.length);
			buf.put(data).flip();
			ByteBuffer texture = stbi_load_from_memory(buf, w, h, c, STBI_rgb_alpha);
			width = w.get(0);
			height = h.get(0);
			memFree(buf);
			id = glCreateTextures(GL_TEXTURE_2D);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			glTextureStorage2D(id, 3, GL_RGBA8, width, height);
			glTextureSubImage2D(id, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, texture);
			glGenerateTextureMipmap(id);
			glTextureParameteri(id, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTextureParameteri(id, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
			glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			stbi_image_free(texture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

	public void cleanUp() {
		glDeleteTextures(id);
	}
}