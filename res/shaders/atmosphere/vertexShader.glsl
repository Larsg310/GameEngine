#version 430

layout (location = 0) in vec3 position;

out vec3 worldPosition;

uniform mat4 mvpMatrix;
uniform mat4 worldMatrix;

void main()
{
    gl_Position = mvpMatrix * vec4(position, 1);
    worldPosition = (worldMatrix * vec4(position, 1)).xyz;
}
