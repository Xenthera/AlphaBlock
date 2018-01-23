package com.bobby;


import com.jogamp.opengl.GL2;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;



public class Main extends PApplet{


    boolean yes = true;
    float count = 0;

    PGraphics gui;



    Chunk chunk;
    PJOGL pgl;
    GL2 gl;
    Player player;

    int[] fogModes; // storage for 3 types of fogs
    int currFogFilter = 1;

    float[] fogColor = { 1.0f, 0.0f, 0.0f, 1.0f };
    public void settings(){
        size(1280,720, P3D);
    }

    public void setup(){
        PGraphicsOpenGL pg = (PGraphicsOpenGL)g;
        println(pg.OPENGL_VERSION);
        chunk = new Chunk(this);
        player = new Player(this, chunk);
        pg.textureSampling(3);
        gui = createGraphics(width, height);
        surface.setResizable(true);
    }

    public void draw(){
        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);



        background(98, 144, 219);
        //background(0,0,0,255);


        perspective(radians(90), (float)width/(float)height, 0.1f, 1000);

        chunk.draw();
        player.draw();

        //gl.glDisable(gl.GL_FOG);

        endPGL();
        if(gui.width != width || gui.height != height) {
            gui.setSize(width, height);
        }

        gui.beginDraw();
        gui.noSmooth();
        gui.noStroke();
        gui.background(0, 0);
        gui.fill(255);
        gui.rect(width/2-2, height/2-15, 4,30);
        gui.rect(width/2-15, height/2-2, 30, 4);
        gui.endDraw();

        pushMatrix();
        resetMatrix();
        ortho();
        image(gui, -gui.width/2,-gui.height/2);
        popMatrix();

    }

    void drawGUI() {
        text(player.position.x, 0, 0);
    }

    public void keyPressed(){
        player.keyPressed();
        if(keyCode == 72) {
            for (int x = 0; x < chunk.CHUNK_WIDTH; x++) {
                for (int y = 0; y < chunk.CHUNK_HEIGHT; y++) {
                    for (int z = 0; z < chunk.CHUNK_LENGTH; z++) {
                        if (y > 0 && y < chunk.CHUNK_HEIGHT - 1 && z > 0 && z < chunk.CHUNK_LENGTH - 1) {

                            chunk.removeBlock(x, y, z);

                        }
                    }
                }
            }
        }else if(key == 't'){
            player.camera.isMouseFocused = false;
        }
    }

    public void keyReleased(){
        player.keyReleased();
    }

    public void mousePressed(){
        player.mousePressed();
    }


    public static void main(String[] args) {
        PApplet.main("com.bobby.Main");
    }
}
