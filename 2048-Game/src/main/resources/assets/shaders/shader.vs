#version 460

out gl_PerVertex 
{ 
    vec4 gl_Position; 
};

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tc;

layout (location = 0) out vec2 TexCoord;

layout (location = 0) uniform mat4 mvp;

void main()
{
    gl_Position = mvp * vec4(pos, 1.0f);
    TexCoord = tc;
}