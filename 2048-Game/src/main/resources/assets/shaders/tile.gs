#version 460

in gl_PerVertex
{
  vec4 gl_Position;
} gl_in[];

out gl_PerVertex
{
  vec4 gl_Position;
};

layout (location = 0) out vec3 TexCoord;

layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

layout (location = 0) uniform mat4 mvp;

void main() {
  vec4 data = gl_in[0].gl_Position;
  gl_Position = mvp * vec4(data.x, data.y, 0.0f, 1.0f);
  TexCoord = vec3(0.0f, 0.0f, data.w);
  EmitVertex();
  gl_Position = mvp * vec4(data.x + data.z, data.y, 0.0f, 1.0f);
  TexCoord = vec3(1.0f, 0.0f, data.w);
  EmitVertex();
  gl_Position = mvp * vec4(data.x, data.y + data.z, 0.0f, 1.0f);
  TexCoord = vec3(0.0f, 1.0f, data.w);
  EmitVertex();
  gl_Position = mvp * vec4(data.x + data.z, data.y + data.z, 0.0f, 1.0f);
  TexCoord = vec3(1.0f, 1.0f, data.w);
  EmitVertex();
  EndPrimitive();
}