uniform mat4 transform;
uniform mat4 modelview;
uniform mat4 projection;
uniform mat3 normalMatrix;
uniform vec3 lightNormal;
uniform mat4 texMatrix;

attribute vec4 position;
attribute vec4 color;
attribute vec3 normal;
attribute vec2 texCoord;

varying vec4 vertColor;
varying vec4 vertTexCoord;
varying vec3 vertNormal;
varying vec3 vertLightDir;

out float frag_distance;
out vec4 vertex_color;

void main() {
  gl_Position = transform * position;

  vertColor = color;
  vertTexCoord = texMatrix * vec4(texCoord, 1.0, 1.0);
  vertNormal = normalize(normalMatrix * normal);
  vertLightDir = -lightNormal;

  frag_distance =
  	(
  		gl_Position.x * gl_Position.x +
  		gl_Position.y * gl_Position.y +
  		gl_Position.z * gl_Position.z
  	);
}