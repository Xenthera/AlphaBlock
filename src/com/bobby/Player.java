package com.bobby;

import com.bobby.Math.Ray;
import com.bobby.Math.RayCaster;
import com.bobby.blocks.BlockStoneBrick;
import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    public FirstPersonCamera camera;
    PVector position;
    PVector velocity;
    public PApplet app;

    public boolean onGround = false;

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
        camera.update();
        checkCollisions();
        camera.draw();

    }

    public void checkCollisions(){
        float padding = 0.3f;
        this.position = this.camera.getPosition();
        this.camera.velocity.add(new PVector(0, world.gravity * (1.0f / app.frameRate), 0));

        if(world.getBlock((int)position.x, (int)position.y + 2, (int)position.z).isSolid()){
            this.camera.velocity.y = 0;

            this.position.y = camera.clamp(this.position.y, this.position.y - 2, ((int)this.position.y + 2) - padding);
            this.onGround = true;
        }
        if(world.getBlock((int)position.x + 1, (int)position.y, (int)position.z).isSolid() || world.getBlock((int)position.x + 1, (int)position.y + 1, (int)position.z).isSolid()){
            float penetration = ((int)this.position.x + 1) - this.position.x;
            if(penetration <= padding) {
                this.camera.velocity.x = 0;
                this.position.x = camera.clamp(this.position.x, this.position.x - 1, ((int)this.position.x + 1) - padding);
            }
        }
        if(world.getBlock((int)position.x - 1, (int)position.y, (int)position.z).isSolid() || world.getBlock((int)position.x - 1, (int)position.y + 1, (int)position.z).isSolid()){
            float penetration = this.position.x - ((int)this.position.x);
            if(penetration <= padding) {
                this.camera.velocity.x = 0;
                this.position.x = camera.clamp(this.position.x, ((int)this.position.x) + padding, this.position.x + 1);
            }
        }
        if(world.getBlock((int)position.x, (int)position.y, (int)position.z + 1).isSolid() || world.getBlock((int)position.x, (int)position.y + 1, (int)position.z + 1).isSolid()){
            float penetration = ((int)this.position.z + 1) - this.position.z;
            if(penetration <= padding) {
                this.camera.velocity.z = 0;
                this.position.z = camera.clamp(this.position.z, this.position.z - 1, ((int)this.position.z + 1) - padding);
            }
        }
        if(world.getBlock((int)position.x, (int)position.y, (int)position.z - 1).isSolid() || world.getBlock((int)position.x, (int)position.y + 1, (int)position.z - 1).isSolid()){
            float penetration = this.position.z - ((int)this.position.z);
            if(penetration <= padding) {
                this.camera.velocity.z = 0;
                this.position.z = camera.clamp(this.position.z, ((int)this.position.z) + padding, this.position.z + 1);
            }
        }
    }
    public void keyPressed(){
        camera.keyPressed();
        if(app.key == ' ' && this.onGround){
            this.camera.velocity.y = -5 * (1.0f / app.frameRate);
            this.onGround = false;
        }
    }

    public void keyReleased(){
        camera.keyReleased();
    }


    public PVector getSightVector(){
        return camera.look;
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
