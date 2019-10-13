package com.bobby;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;
import processing.opengl.PShader;


public class Main extends PApplet{
    private PGraphics gui;
    private World world;

    PFont font;

    PJOGL pgl;

    PShader blockShader;

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

        hint(DISABLE_TEXTURE_MIPMAPS);
        ((PGraphicsOpenGL)g).textureSampling(2);

        blockShader = loadShader("com/bobby/Frag.glsl", "com/bobby/Vert.glsl");

        blockShader.set("fraction", 1.0f);

        if(!world.isLoaded){
            thread("loadWorldThread");
        }

    }

    public void loadWorldThread(){
        world.load();
        player.camera.centerMouse();
        PVector spawn = world.getRandomSpawnPoint();
        player.setPosition(spawn.x - 0.5f, spawn.y - 1.5f, spawn.z - 0.5f);
    }

    public void draw(){

        if(world.isLoading){
            background(0);
            textAlign(CENTER, BOTTOM);
            //text("Loading World", width / 2, height / 2);
            text("Loading:\n" + this.world.progressType, width / 2, height / 2);
            noFill();
            stroke(255);
            strokeWeight(6);
            rect(width / 3 - 10, height / 2 + 30, (width / 3) + 20, 70, 0);
            fill(0,100,255);
            noStroke();
            rect(width / 3, height / 2 + 40, world.progress * (width / 3), 50);
            return;
        }

        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);

        background(98, 144, 219);

        perspective(radians(90), (float)width/(float)height, 0.1f, 1000);



        shader(blockShader);
        world.draw();
        resetShader();

        player.draw();

        endPGL();
        if(gui.width != width || gui.height != height) {
            gui.setSize(width, height);
        }

        pushMatrix();
        resetMatrix();
        ortho();
        noLights();
        translate(-width / 2, -height / 2);
        drawGUI();
        popMatrix();

    }

    private void drawGUI() {
        //graphics.noSmooth();
        pushStyle();
        noStroke();
        fill(255);
        rect(width/2-2, height/2-15, 4,30);
        rect(width/2-15, height/2-2, 30, 4);
        stroke(0);
        fill(255);
        textAlign(LEFT, TOP);
        textFont(font, 20);
        text("FPS: " + (int)frameRate, 10, 10);
        text("X: " + String.format("%.02f", player.position.x), 10,28);
        text("Y: " + String.format("%.02f", player.position.y), 10,46);
        text("Z: " + String.format("%.02f", player.position.z), 10,64);
        text("L: Lighting on/off", 10,82);
        text("F: Show Mouse", 10,100);
        popStyle();
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
