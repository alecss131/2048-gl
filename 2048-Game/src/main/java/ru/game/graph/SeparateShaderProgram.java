package ru.game.graph;

import static org.lwjgl.opengl.GL43.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SeparateShaderProgram {
	private int id;
	
	public void createVertexShader(String shaderCode) {
        id = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) {
        id = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }
    
    public void createGeometryShader(String shaderCode) {
        id = createShader(shaderCode, GL_GEOMETRY_SHADER);
    }
    
    public void createTessellationControlShader(String shaderCode) {
        id = createShader(shaderCode, GL_TESS_CONTROL_SHADER);
    }
    
    public void createTessellationEvaluationShader(String shaderCode) {
        id = createShader(shaderCode, GL_TESS_EVALUATION_SHADER);
    }
    
    public void createComputeShader(String shaderCode) {
        id = createShader(shaderCode, GL_COMPUTE_SHADER);
    }
    
    public int getId() {
    	return id;
    }
    
    public void cleanUp() {
        if (id != 0) {
            glDeleteProgram(id);
        }
    }
    
	/**
	 * for compute shader only
	 */
	public void bind() { //
		glUseProgram(id);
	}

	/**
	 * for compute shader only
	 */
	public void unbind() {
		glUseProgram(0);
	}
	
	private int createShader(String shaderCode, int shaderType) {
		int shaderId = glCreateShaderProgramv(shaderType, shaderCode);
		if (shaderId == 0) {
            throw new RuntimeException("Error creating shader.");
        }
		if (glGetProgrami(shaderId, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("Error validating shader: " + glGetProgramInfoLog(shaderId, 1024));
        }
		return shaderId;
	}
	
	public void setUniform(int location, Matrix4f value) {
		glProgramUniformMatrix4fv(id, location, false, value.get(new float[16]));
	}
	
	public void setUniform(int location, Vector4f value) {
		glProgramUniform4fv(id, location, new float[]{value.x, value.y, value.z, value.w});
	}
	
	public void setUniform(int location, Vector3f value) {
		glProgramUniform3fv(id, location, new float[]{value.x, value.y, value.z});
	}
	
	public void setUniform(int location, Vector2f value) {
		glProgramUniform2fv(id, location, new float[]{value.x, value.y});
	}
	
	public void setUniform(int location, int value) {
	    glProgramUniform1i(id, location, value);
	}
	
	public void setUniform(int location, float value) {
	    glProgramUniform1f(id, location, value);
	}
}
