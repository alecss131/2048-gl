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

public class TextureArray {
	private int width;
	private int height;
	private int id;
	
	public TextureArray(BufferedImage[] images) {
		try (MemoryStack stack = stackPush(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			id = glCreateTextures(GL_TEXTURE_2D_ARRAY);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			for (int i = 0; i < images.length; i++) {
				ImageIO.write(images[i], "png", baos);
				baos.flush();
				byte[] data = baos.toByteArray();
				ByteBuffer buf = memAlloc(data.length);
				buf.put(data).flip();
				ByteBuffer texture = stbi_load_from_memory(buf, w, h, c, STBI_rgb_alpha);
				width = w.get(0);
				height = h.get(0);
				if (i==0) {
					glTextureStorage3D(id, 3, GL_RGBA8, width, height, images.length);
				}
				memFree(buf);
				glTextureSubImage3D(id, 0, 0, 0, i, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, texture);
				stbi_image_free(texture);
				baos.reset();
			}
			glGenerateTextureMipmap(id);
			glTextureParameteri(id, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTextureParameteri(id, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
			glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
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
        glBindTexture(GL_TEXTURE_2D_ARRAY, id);
    }
    
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D_ARRAY, 0);
    }
	
	public void cleanUp() {
		glDeleteTextures(id);
	}
}