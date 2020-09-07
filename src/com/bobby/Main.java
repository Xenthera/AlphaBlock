package com.bobby;

import com.bobby.blocks.*;
import com.bobby.blocks.construction.BlockGeometry;
import processing.core.*;
import processing.event.MouseEvent;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;
import processing.opengl.PShader;


public class Main extends PApplet{
    private PGraphics depthBuffer;
    private World world;

    PFont font;

    PJOGL pgl;

    PShader blockShader, blockShaderUnlit, depthBufferShader, blockBlackAndWhiteShader;

    PShape loadingMesh;

    MemoryProfiler profiler;

    PGraphicsOpenGL pg;

    private boolean lightingEnabled = true;
    PVector lightDir = new PVector();
    private Player player;

    public void settings(){
        size(1280,720, P3D);
        noSmooth();
    }

    public void setup(){
        pg = (PGraphicsOpenGL)g;
        println(PGraphicsOpenGL.OPENGL_VERSION);
        textureMode(NORMAL);
        background(0);


        profiler = new MemoryProfiler();

        world = new World(this);
        player = new Player(this, world);

        //pg.textureSampling(3);
        depthBuffer = createGraphics(1024, 1024, P3D);
        surface.setResizable(true);

        font = loadFont("VCR48.vlw");
        textFont(font, 45);

        hint(DISABLE_TEXTURE_MIPMAPS);
        ((PGraphicsOpenGL)g).textureSampling(2);

        blockShader = loadShader("com/bobby/Shaders/Frag.glsl", "com/bobby/Shaders/Vert.glsl");

        //blockBlackAndWhiteShader = loadShader("com/bobby/Shaders/FragBlackAndWhite.glsl", "com/bobby/Shaders/VertBlackAndWhite.glsl");

        blockShaderUnlit = loadShader("com/bobby/Shaders/FragUnlit.glsl", "com/bobby/Shaders/VertUnlit.glsl");

        depthBufferShader = loadShader("com/bobby/Shaders/FragDepth.glsl", "com/bobby/Shaders/VertDepth.glsl");



        if(!world.isLoaded){
            thread("loadWorldThread");
        }
        Block loadingBlock = new BlockGrass();
        Block loadingBlock2 = new BlockFlower();
        loadingMesh = createShape();
        loadingMesh.beginShape(PConstants.TRIANGLE);
        loadingMesh.texture(world.textureManager.getTextureAtlas());
        BlockGeometry.constructBlock(this, world.textureManager, loadingBlock, loadingMesh, true, true, true, true, true, true, 0, 0, 0);
        BlockGeometry.constructBlock(this, world.textureManager, loadingBlock2, loadingMesh, true, true, true, true, true, true, 0, -1, 0);

        loadingMesh.noStroke();
        loadingMesh.endShape();
    }

    public void loadWorldThread(){
        world.load();
        player.camera.centerMouse();
        PVector spawn = world.getRandomSpawnPoint();
        player.setPosition(spawn.x - 0.5f, spawn.y - 1.5f, spawn.z - 0.5f);
    }

    void loadingDraw(){
        background(0);
        pushMatrix();
        resetMatrix();
        perspective(radians(50), (float)width/(float)height, 0.1f, 1000);
        noFill();
        stroke(255);
        translate(0, -2.2f, -8);
        rotate(millis() * 0.001f, 0, 1, 0);
        translate(-0.5f, 0, -0.5f);
        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);

        shader(blockShaderUnlit);
        shape(this.loadingMesh);
        resetShader();
        endPGL();
        popMatrix();

        ortho();


        profiler.draw(this, 10, 10);
        textFont(font, 45);
        textAlign(CENTER, CENTER);
        fill(0,100,255);
        text("Loading:\n" + this.world.progressType, width / 2, height / 2);
        noStroke();
        rect(width / 3, height / 3 * 2 + 10, world.progress * (width / 3), 50);
        noFill();
        stroke(255);
        strokeWeight(6);
        rect(width / 3 - 10, height / 3 * 2, (width / 3) + 20, 70, 0);

    }


    public void draw(){

        float lightAngle = frameCount * 0.002f;
        lightDir.set(sin(lightAngle) * 160, 160, cos(lightAngle) * 160);

        if(world.isLoading){
            loadingDraw();
            return;
        }

        //--------------------------------
        //Main Pass
        //--------------------------------
        pgl = (PJOGL) beginPGL();
        pgl.frontFace(PGL.CCW);
        pgl.enable(PGL.CULL_FACE);
        player.update();
        background(98, 144, 219);
        perspective(radians(90), (float)width/(float)height, 0.1f, 400);
        //shader(lightingEnabled ? blockShader : blockShaderUnlit);
        //shader(lightingEnabled ? blockShader : depthBufferShader);
        world.draw(this.getGraphics(), blockShader, depthBufferShader);

        player.draw(this.getGraphics());

        endPGL();

        resetShader();

        //--------------------------------
        //GUI Pass
        //--------------------------------

        pushMatrix();
        resetMatrix();
        ortho();
        translate(-width / 2, -height / 2);
        drawGUI();

        popMatrix();

    }

    private void drawGUI() {

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
        profiler.draw(this, 10, 118);
        player.drawGUI(this.getGraphics(), this.world.textureManager);
        popStyle();
    }

    public void keyPressed(){
        player.keyPressed();
        if(key == 'f'){
            player.camera.isMouseFocused = false;
        }else if(key == 'l'){
            lightingEnabled = !lightingEnabled;
            System.out.println(lightingEnabled);
        }
    }

    public void keyReleased(){
        player.keyReleased();
    }

    public void mousePressed(){
        player.mousePressed();
    }

    public void mouseWheel(MouseEvent event){
        player.mouseWheel(event);
    }

    public static void main(String[] args) {
        PApplet.main("com.bobby.Main");
    }
}
