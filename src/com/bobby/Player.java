package com.bobby;

import com.bobby.Math.Ray;
import com.bobby.Math.RayCaster;
import com.bobby.blocks.BlockStoneBrick;
import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    public FirstPersonCamera camera;
    PVector position;
    public PApplet app;

    RayCaster rayCaster;
    World world;

    public Player(PApplet app, World world) {
        this.world = world;
        this.camera = new FirstPersonCamera(app);
        this.position = this.camera.getPosition();
        this.app = app;
        rayCaster = new RayCaster(this.app, this.world);

    }

    public void draw(){
        drawFocusedBlock();
        camera.draw();

    }
    public void keyPressed(){
        camera.keyPressed();
    }

    public void keyReleased(){
        camera.keyReleased();
    }


    public PVector getSightVector(){
        return camera.forward.normalize();
    }





    private void drawFocusedBlock(){
        PVector vector = getSightVector();
        Ray ray = rayCaster.traceRay(this.position, vector, 80);
        PVector position = ray.getHitPostition();
        if(ray.hasTarget()){
            app.pushStyle();
            app.fill(0,0,255,50);
            app.translate(0.5f + position.x,0.5f + position.y,0.5f + position.z);
            app.box(1.01f);
            app.popStyle();
        }


    }

    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;

    }


    public void mousePressed(){


        PVector vector = getSightVector();
        Ray ray = rayCaster.traceRay(this.position, vector, 80);
        PVector position = ray.getHitPostition();
        PVector normal = ray.getHitNormal();
        if (camera.isMouseFocused) {
            if (ray.hasTarget()) {
                if (app.mouseButton == app.LEFT) {
                    world.removeBlock((int) position.x, (int) position.y, (int) position.z, true);
                } else if (app.mouseButton == app.RIGHT) {
                    world.setBlock(new BlockStoneBrick(), (int)position.x + (int)normal.x, (int) position.y + (int)normal.y, (int) position.z + (int)normal.z, true);
                }
            }
        }

        if(camera.isMouseFocused == false){
            camera.centerMouse();
            camera.isMouseFocused = true;
        }
    }
}
