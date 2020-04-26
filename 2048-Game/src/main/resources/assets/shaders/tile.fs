#version 460

layout (location = 0) in vec3 TexCoord;

layout (location = 0) out vec4 color;

layout (location = 0) uniform sampler2DArray sampler;

void main()
{
    color = texture(sampler, TexCoord);
}