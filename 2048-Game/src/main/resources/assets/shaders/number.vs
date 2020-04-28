#version 460

out gl_PerVertex 
{ 
    vec4 gl_Position; 
};

layout (location = 0) in ivec4 pos;
layout (location = 1) in int cel;

layout (location = 0) out int num;

void main()
{
    gl_Position = vec4(pos.x + gl_InstanceID * pos.z, pos.y, pos.z, pos.w);
    num = cel;
}