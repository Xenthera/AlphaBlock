////#ifdef GL_ES
////precision mediump float;
////precision mediump int;
////#endif
////
////const vec3 sky_color = vec3(98.0f / 255.0f, 144.0f / 255.0f, 219.0f / 255.0f);
////
////uniform sampler2D texture;
////
////varying vec4 vertTexCoord;
////
////uniform float fraction;
////
////varying vec4 vertColor;
////varying vec3 vertNormal;
////varying vec3 vertLightDir;
////
////in float frag_distance;
////
////in vec4 vertex_color;
////void main() {
////
////    vec4 fragColor = texture2D(texture, vertTexCoord.st);
////
////    //float frag_lighting = rand(vec2(0, frag_distance));
////
////    if(fragColor.w == 0.0f){
////        discard;
////    }
//////    gl_FragColor = vec4(mix(fragColor.xyz * vertex_color.xyz, sky_color, min(1.0f, frag_distance / 1000)), fragColor.w);
////
////
////    float depth = gl_FragCoord.z;//(gl_FragCoord.z / gl_FragCoord.w) / 100;
////
////    gl_FragColor = vec4( depth, depth, depth, 1.0f);
////    //gl_FragColor = vec4(mix(fragColor.xyz * vertex_color.xyz, sky_color, min(1.0f, frag_distance / 30000)), fragColor.w);
////
////}
//
//vec4 packDepth(float depth) {
//    float depthFrac = fract(depth * 255.0);
//    return vec4(depth - depthFrac / 255.0, depthFrac, 1.0, 1.0);
//}
//
//void main(void) {
//    gl_FragColor = packDepth(gl_FragCoord.z);
//}

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

const vec3 sky_color = vec3(98.0f / 255.0f, 144.0f / 255.0f, 219.0f / 255.0f);

uniform sampler2D texture;
uniform sampler2D shadowMap;

varying vec4 vertTexCoord;

uniform float fraction;

varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;

varying vec4 ShadowCoord;

in float frag_distance;

in vec4 vertex_color;

void main() {

    vec4 fragColor = texture2D(texture, vertTexCoord.st);
    float gray = (fragColor.r + fragColor.g + fragColor.b) / 3f;
    fragColor = vec4(gray, gray, gray, fragColor.a);

    //float frag_lighting = rand(vec2(0, frag_distance));

    if(fragColor.w == 0.0f){
        discard;
    }
    //gl_FragColor = vec4(mix(fragColor.xyz * vertex_color.xyz, sky_color, min(1.0f, frag_distance / 1000)), fragColor.w);

    //    float visibility = 1.0;
    //    if ( texture2D( shadowMap, ShadowCoord.xy ).z  <  ShadowCoord.z){
    //        visibility = 0.5;
    //    }
    //    float depth = (gl_FragCoord.z / gl_FragCoord.w) / 100;

    gl_FragColor = vec4(mix(fragColor.xyz * vertex_color.xyz, sky_color, min(1.0f, frag_distance / 16000)), fragColor.w);
    //gl_FragColor = vec4(visibility, visibility, visibility, 1.0f);


}


