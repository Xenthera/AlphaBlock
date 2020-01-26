#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

const vec3 sky_color = vec3(98.0f / 255.0f, 144.0f / 255.0f, 219.0f / 255.0f);

uniform sampler2D texture;

varying vec4 vertTexCoord;

uniform float fraction;

varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;

in float frag_distance;

void main() {

    vec4 fragColor = texture2D(texture, vertTexCoord.st);

    //float frag_lighting = rand(vec2(0, frag_distance));

    if(fragColor.w == 0.0f){
        discard;
    }


    gl_FragColor = fragColor;
}
