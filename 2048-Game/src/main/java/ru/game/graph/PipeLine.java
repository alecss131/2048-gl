package ru.game.graph;

import static org.lwjgl.opengl.GL43.*;

public class PipeLine {
	private int id;
	
	public PipeLine() {
		id = glGenProgramPipelines();
		if (id == 0) {
			throw new RuntimeException("Could not create PipeLine");
		}
	}
	
	public void setVertexStage(int shader) {
        glUseProgramStages(id, GL_VERTEX_SHADER_BIT, shader);
    }

    public void setFragmentStage(int shader) {
        glUseProgramStages(id, GL_FRAGMENT_SHADER_BIT, shader);
    }
    
    public void setGeometryStage(int shader) {
        glUseProgramStages(id, GL_GEOMETRY_SHADER_BIT, shader);
    }
    
    public void setTessellationControlStage(int shader) {
        glUseProgramStages(id, GL_TESS_CONTROL_SHADER_BIT, shader);
    }
    
    public void setTessellationEvaluationStage(int shader) {
        glUseProgramStages(id, GL_TESS_EVALUATION_SHADER_BIT, shader);
    }
    
    public void setComputeStage(int shader) {
        glUseProgramStages(id, GL_COMPUTE_SHADER_BIT, shader);
    }
	
	public void bind() {
		glBindProgramPipeline(id);
	}
	
	public void unbind() {
		glBindProgramPipeline(0);
	}
	
	public void validate() {
		glValidateProgramPipeline(id);
		int s = glGetProgramPipelinei(id, GL_VALIDATE_STATUS);
		if (s == 0) {
			throw new RuntimeException("Error validate pipeline: " + glGetProgramPipelineInfoLog(id, 1024));
		}
	}
	
	public void cleanUp() {
        unbind();
        if (id != 0) {
        	glDeleteProgramPipelines(id);
        }
    }
}