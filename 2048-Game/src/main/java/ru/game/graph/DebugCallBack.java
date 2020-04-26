package ru.game.graph;

import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;
import java.nio.IntBuffer;

public class DebugCallBack implements GLDebugMessageCallbackI {
	private boolean info = false;
	
	DebugCallBack() {
		if (info) {
			try (MemoryStack stack = stackPush()) {
				String renderer = glGetString(GL_RENDERER);
				String vendor = glGetString(GL_VENDOR);
				String version = glGetString(GL_VERSION);
				String glslVersion = glGetString(GL_SHADING_LANGUAGE_VERSION);
				IntBuffer maj = stack.mallocInt(1);
				IntBuffer min = stack.mallocInt(1);
				glGetIntegerv(GL_MAJOR_VERSION, maj);
				glGetIntegerv(GL_MINOR_VERSION, min);
				int major = maj.get(0);
				int minor = min.get(0);
				System.out.println("OpenGl Info");
				System.out.printf("GL Vendor : %s\n", vendor);
				System.out.printf("GL Renderer : %s\n", renderer);
				System.out.printf("GL Version (string) : %s\n", version);
				System.out.printf("GL Version (integer) : %d.%d\n", major, minor);
				System.out.printf("GLSL Version : %s\n", glslVersion);
			}
		}
	}

	@Override
	public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
		System.out.printf("%s:%s[%s](%d): %s\n", source(source), type(type), severity(severity), id,
				memUTF8(memByteBuffer(message, length)));
	}

	private String source(int source) {
		switch (source) {
		case GL_DEBUG_SOURCE_API:
			return "API";
		case GL_DEBUG_SOURCE_WINDOW_SYSTEM:
			return "WINDOW SYSTEM";
		case GL_DEBUG_SOURCE_SHADER_COMPILER:
			return "SHADER COMPILER";
		case GL_DEBUG_SOURCE_THIRD_PARTY:
			return "THIRD PARTY";
		case GL_DEBUG_SOURCE_APPLICATION:
			return "APPLICATION";
		case GL_DEBUG_SOURCE_OTHER:
			return "OTHER";
		default:
			return "UNKNOWN";
		}
	}

	private String type(int type) {
		switch (type) {
		case GL_DEBUG_TYPE_ERROR:
			return "ERROR";
		case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
			return "DEPRECATED_BEHAVIOR";
		case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
			return "UNDEFINED_BEHAVIOR";
		case GL_DEBUG_TYPE_PORTABILITY:
			return "PORTABILITY";
		case GL_DEBUG_TYPE_PERFORMANCE:
			return "PERFORMANCE";
		case GL_DEBUG_TYPE_MARKER:
			return "MARKER";
		case GL_DEBUG_TYPE_OTHER:
			return "OTHER";
		default:
			return "UNKNOWN";
		}
	}

	private String severity(int severity) {
		switch (severity) {
		case GL_DEBUG_SEVERITY_NOTIFICATION:
			return "NOTIFICATION";
		case GL_DEBUG_SEVERITY_LOW:
			return "LOW";
		case GL_DEBUG_SEVERITY_MEDIUM:
			return "MEDIUM";
		case GL_DEBUG_SEVERITY_HIGH:
			return "HIGH";
		default:
			return "UNKNOWN";
		}
	}
}