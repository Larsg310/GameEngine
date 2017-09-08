#version 430

in vec3 worldPosition;

layout (location = 0) out vec4 outColor;

const vec3 baseColor = vec3(0.18,0.27,0.47);

void main()
{
    float red = -0.00022 * (worldPosition.y - 2800) + baseColor.x;
    float green = -0.00025 * (worldPosition.y - 2800) + baseColor.y;
    float blue = -0.00019 * (worldPosition.y - 2800) + baseColor.z;

    outColor = vec4(red,green,blue,1);
}