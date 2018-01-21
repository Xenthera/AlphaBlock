package com.bobby;

import processing.core.PApplet;
import processing.opengl.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;

public class Main extends PApplet{


    boolean yes = true;
    float count = 0;



    Chunk chunk;
    PJOGL pgl;
    GL2ES2 gl;
    QueasyCam cam;
    public void settings(){
        size(800,600, P3D);
    }

    public void setup(){
        chunk = new Chunk(this);
        cam = new QueasyCam(this);
        cam.sensitivity = 0.25f;
        ((PGraphicsOpenGL)g).textureSampling(2);
    }

    public void draw(){
        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);
        pgl.cullFace(PGL.BACK);



        background(98, 144, 219);

        perspective(radians(75), (float)width/(float)height, 1, 10000);

        count += 0.01;
        translate(width/2, height/2);


        noStroke();
        pushMatrix();
        scale(40);

        //rotateX(count);
        chunk.removeBlock((int)random(chunk.CHUNK_WIDTH),(int)random(chunk.CHUNK_HEIGHT),(int)random(chunk.CHUNK_LENGTH));
        chunk.draw();

        popMatrix();
        endPGL();
    }

    void drawGUI() {
        text(cam.position.x, 0, 0);
    }

    public void keyPressed(){
        for (int x = 0; x < chunk.CHUNK_WIDTH; x++) {
            for (int y = 0; y < chunk.CHUNK_HEIGHT; y++) {
                for (int z = 0; z < chunk.CHUNK_LENGTH; z++) {
                    if(y > 0 && y < chunk.CHUNK_HEIGHT - 1 && z > 0 && z < chunk.CHUNK_LENGTH - 1){
                        if(keyCode == 72){
                            chunk.removeBlock(x,y,z);
                            chunk.removeBlock(x + 1, y + 1, z + 1);
                            chunk.removeBlock(x - 1, y - 1, z - 1);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("com.bobby.Main");
    }
}
