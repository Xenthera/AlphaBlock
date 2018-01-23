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
    Chunk chunk;
    RayCaster rayCaster;

    public Player(PApplet app, Chunk chunk) {
        this.camera = new FirstPersonCamera(app);
        this.position = this.camera.getPosition();
        this.chunk = chunk;
        this.app = app;
        rayCaster = new RayCaster(this.app, this.chunk);

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
            app.fill(0,0,255,50);
            app.translate(0.5f + position.x,0.5f + position.y,0.5f + position.z);
            app.box(1.01f);
        }


    }


    public void mousePressed(){


        PVector vector = getSightVector();
        Ray ray = rayCaster.traceRay(this.position, vector, 80);
        PVector position = ray.getHitPostition();
        PVector normal = ray.getHitNormal();
        if (camera.isMouseFocused) {
            if (ray.hasTarget()) {
                if (app.mouseButton == app.LEFT) {
                    chunk.removeBlock((int) position.x, (int) position.y, (int) position.z);
                } else if (app.mouseButton == app.RIGHT) {
                    chunk.setBlock(new BlockStoneBrick(), (int)position.x + (int)normal.x, (int) position.y + (int)normal.y, (int) position.z + (int)normal.z);
                }
            }
        }

        if(camera.isMouseFocused == false){
            camera.isMouseFocused = true;
        }
    }
}
