#version 460

out gl_PerVertex 
{ 
    vec4 gl_Position; 
};

layout (location = 0) in ivec4 pos;
layout (location = 1) in int cel;

void main()
{
    int x = gl_InstanceID % 4;
    int y = gl_InstanceID / 4;
    gl_Position = vec4(pos.x + x * pos.z + (x+1) * pos.w, pos.y + y * pos.z + (y+1) * pos.w, pos.z, cel);
}