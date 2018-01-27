package com.bobby;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;



public class Main extends PApplet{
    private PGraphics gui;
    private World world;

    PFont font;

    PJOGL pgl;

    private boolean lightingEnabled = false;

    private Player player;

    public void settings(){
        size(1280,720, P3D);
        noSmooth();
    }

    public void setup(){
        PGraphicsOpenGL pg = (PGraphicsOpenGL)g;
        println(PGraphicsOpenGL.OPENGL_VERSION);
        textureMode(NORMAL);
        background(0);

        world = new World(this);
        player = new Player(this, world);

        //pg.textureSampling(3);
        gui = createGraphics(width, height);
        surface.setResizable(true);

        font = loadFont("VCR48.vlw");
        textFont(font, 45);
        textAlign(CENTER, CENTER);
        text("LOADING",width/2,height/2);

        hint(DISABLE_TEXTURE_MIPMAPS);
        ((PGraphicsOpenGL)g).textureSampling(2);
    }

    public void draw(){
        if(!world.isLoaded){
            world.load();
            player.camera.centerMouse();
            PVector spawn = world.getRandomSpawnPoint();
            player.setPosition(spawn.x - 0.5f, spawn.y - 1.5f, spawn.z - 0.5f);
        }

        if(lightingEnabled) {
            directionalLight(200, 200, 200, 0, -0.75f, 0);
            ambientLight(98, 144, 219);
        }
        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);





        background(98, 144, 219);
        //background(0,0,0,255);


        perspective(radians(90), (float)width/(float)height, 0.1f, 1000);

        world.draw();
        player.draw();

        //gl.glDisable(gl.GL_FOG);

        endPGL();
        if(gui.width != width || gui.height != height) {
            gui.setSize(width, height);
        }

        gui.beginDraw();
        drawGUI(gui);
        gui.endDraw();

        pushMatrix();
        resetMatrix();
        ortho();
        noLights();
        image(gui, -gui.width/2,-gui.height/2);
        popMatrix();

    }

    private void drawGUI(PGraphics graphics) {
        //graphics.noSmooth();
        gui.pushStyle();
        gui.noStroke();
        gui.background(0, 0);
        gui.fill(255);
        gui.rect(width/2-2, height/2-15, 4,30);
        gui.rect(width/2-15, height/2-2, 30, 4);
        gui.stroke(0);
        gui.fill(255);
        gui.textAlign(LEFT, TOP);
        gui.textFont(font, 20);
        gui.text("FPS: " + (int)frameRate, 10, 10);
        gui.text("X: " + String.format("%.02f", player.position.x), 10,28);
        gui.text("Y: " + String.format("%.02f", player.position.y), 10,46);
        gui.text("Z: " + String.format("%.02f", player.position.z), 10,64);
        gui.text("L: Lighting on/off", 10,82);
        gui.text("F: Show Mouse", 10,100);
        gui.popStyle();
    }

    public void keyPressed(){
        player.keyPressed();
        if(key == 'f'){
            player.camera.isMouseFocused = false;
        }else if(key == 'l'){
            lightingEnabled = !lightingEnabled;
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
