#version 460

layout (location = 0) in vec2 TexCoord;

layout (location = 0) out vec4 color;

layout (location = 0) uniform sampler2D sampler;

void main()
{
    color = texture(sampler, TexCoord);
}