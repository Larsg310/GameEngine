#version 430

layout(triangles) in;
layout(line_strip, max_vertices = 4) out;

uniform mat4 m_ViewProjection;

//float rand(vec2 n) {
//	return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453);
//}
//
//float noise(vec2 p){
//	vec2 ip = floor(p);
//	vec2 u = fract(p);
//	u = u*u*(3.0-2.0*u);
//
//	float res = mix(mix(rand(ip),rand(ip+vec2(1.0,0.0)),u.x),mix(rand(ip+vec2(0.0,1.0)),rand(ip+vec2(1.0,1.0)),u.x),u.y);
//	return res*res;
//}

void main() {

	for (int i = 0; i < gl_in.length(); ++i)
	{
		vec4 pos = gl_in[i].gl_Position;
		//pos.y = noise(pos.xz) * 2;
		gl_Position = m_ViewProjection * pos;
		EmitVertex();
	}

	vec4 pos = gl_in[0].gl_Position;
	//pos.y = noise(pos.xz) * 2;
	gl_Position = m_ViewProjection * pos;
    EmitVertex();

	EndPrimitive();
}