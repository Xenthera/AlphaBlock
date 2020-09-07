package com.bobby;

import com.bobby.Math.Ray;
import com.bobby.Math.RayCaster;
import com.bobby.blocks.Block;
import com.bobby.blocks.BlockManager;
import com.bobby.blocks.BlockSandstone;
import com.bobby.blocks.BlockStoneBrick;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.HashMap;

public class Player {
    FirstPersonCamera camera;
    PVector position;
    PVector velocity;
    private PApplet app;
    HashMap<Character, Boolean> keys;
    private float friction = 0.75f;

    int curBlock = 0;

    float speed;

    boolean gravityOn = false;

    public boolean onGround = false;

    RayCaster rayCaster;
    World world;

    public Player(PApplet app, World world) {

        this.world = world;
        this.camera = new FirstPersonCamera(app, this);
        this.position = new PVector(0, 0, 0);
        this.app = app;
        rayCaster = new RayCaster(this.app, this.world);
        this.keys = new HashMap<>();
        velocity = new PVector(0f, 0f, 0f);
    }

    public void draw(PGraphics graphics) {
        this.drawFocusedBlock(graphics);



        this.camera.draw(graphics);

    }

    public void drawGUI(PGraphics graphics, TextureManager manager){
        graphics.fill(0,100,255);
        //graphics.text(BlockManager.BLOCKS[curBlock].getName(), 10, graphics.height - 20);
        int numBoxes = 17;
        int boxSize = 40;
        int pad = 2;
        int barSize = boxSize * numBoxes + (numBoxes * pad);

        graphics.strokeWeight(pad);

        graphics.noFill();
        for (int i = 0; i < numBoxes; i++) {
            graphics.stroke(0, 255, 128);
            graphics.rect((graphics.width / 2) - (barSize / 2) + (i * boxSize + pad), graphics.height - (boxSize + pad) - 5, boxSize, boxSize);
            if(i < BlockManager.BLOCKS.length)
            graphics.image(manager.textureAtlas, (graphics.width / 2) - (barSize / 2) + (i * boxSize + pad), graphics.height - (boxSize  + pad) - 5, boxSize, boxSize, BlockManager.BLOCKS[i].texture.SIDES[0] * 16, BlockManager.BLOCKS[i].texture.SIDES[1] * 16, BlockManager.BLOCKS[i].texture.SIDES[0] * 16 + 16, BlockManager.BLOCKS[i].texture.SIDES[1] * 16 + 16  );

        }

        graphics.stroke(255);
        graphics.rect((graphics.width / 2) - (barSize / 2) + ((curBlock % numBoxes) * boxSize + pad) - 5, graphics.height - (boxSize + pad) - 5 - 5, boxSize + 10, boxSize + 10);

    }

    public void mouseWheel(MouseEvent event){
        this.curBlock += event.getCount();

        if(curBlock > BlockManager.BLOCKS.length - 1){
            curBlock = 0;
        }

        if(curBlock < 0){
            curBlock = BlockManager.BLOCKS.length - 1;
        }
    }

    public void update() {
        if(this.app.keyPressed && this.app.keyCode == 16){
            speed = 4.5f * (1.0f / app.frameRate);
        }else {
            speed = 1.5f * (1.0f / app.frameRate);
        }
        if (keys.containsKey('a') && keys.get('a')) velocity.add(PVector.mult(camera.right, speed));
        if (keys.containsKey('d') && keys.get('d')) velocity.sub(PVector.mult(camera.right, speed));
        if (keys.containsKey('w') && keys.get('w')) velocity.add(PVector.mult(camera.forward, speed));
        if (keys.containsKey('s') && keys.get('s')) velocity.sub(PVector.mult(camera.forward, speed));
        if (keys.containsKey('q') && keys.get('q')) velocity.add(PVector.mult(camera.up, speed));
        if (keys.containsKey('e') && keys.get('e')) velocity.sub(PVector.mult(camera.up, speed));
        velocity.x *= friction;
        velocity.z *= friction;
        if(gravityOn) {
            this.velocity.add(new PVector(0, world.gravity * (1.0f / app.frameRate), 0));
        }else{
            velocity.y *= friction;
        }
        position = this.checkCollisions(this.position.add(this.velocity));

        this.camera.update();
    }

    public float clamp(float x, float min, float max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    public PVector checkCollisions(PVector position) {
        PVector newPosition = position;
        float paddingP = 0.75f;
        float paddingN = 1 - paddingP;


        if (world.getBlock((int) position.x, (int) (position.y + 1.5f), (int) position.z).isSolid()) {
            this.velocity.y = 0;

            this.position.y = (int) this.position.y + 0.5f;
            this.onGround = true;
        }
        if (world.getBlock((int) position.x, (int) (position.y - 1), (int) position.z).isSolid()) {
            float penetration = (int) this.position.y - this.position.y;
            if (-penetration < paddingN) {
                this.velocity.y = 0;
                newPosition.y += (paddingN + penetration);
            }
        }
        if (world.getBlock((int) position.x + 1, (int) position.y, (int) position.z).isSolid() || world.getBlock((int) position.x + 1, (int) position.y + 1, (int) position.z).isSolid()) {
            float penetration = (this.position.x - (int) this.position.x) * 1;
            if (!(penetration < paddingP)) {
                newPosition.x -= (penetration - paddingP) * 1;
            }
        }

        if (world.getBlock((int) position.x - 1, (int) position.y, (int) position.z).isSolid() || world.getBlock((int) position.x - 1, (int) position.y + 1, (int) position.z).isSolid()) {
            //float penetration = (this.position.x - (int)this.position.x - 1) * 1;
            float penetration = (int) this.position.x - this.position.x;
            if (-penetration < paddingN) {
                newPosition.x += (paddingN + penetration);
            }
        }

        if (world.getBlock((int) position.x, (int) position.y, (int) position.z + 1).isSolid() || world.getBlock((int) position.x, (int) position.y + 1, (int) position.z + 1).isSolid()) {
            float penetration = (this.position.z - (int) this.position.z) * 1;
            if (!(penetration < paddingP)) {
                newPosition.z -= (penetration - paddingP) * 1;
            }
        }
        if (world.getBlock((int) position.x, (int) position.y, (int) position.z - 1).isSolid() || world.getBlock((int) position.x, (int) position.y + 1, (int) position.z - 1).isSolid()) {
            //float penetration = (this.position.x - (int)this.position.x - 1) * 1;
            float penetration = (int) this.position.z - this.position.z;
            if (-penetration < paddingN) {
                newPosition.z += (paddingN + penetration);
            }
        }
        return newPosition;
    }

    public void keyPressed() {
        char key = app.key;
        keys.put(Character.toLowerCase(key), true);

        if (app.key == ' ' && onGround) {
            this.velocity.y += -7 * (1.0f / app.frameRate);
            onGround = false;
        }



    }

    public void keyReleased() {
        char key = app.key;
        keys.put(Character.toLowerCase(key), false);
    }

    public PVector getSightVector() {
        return camera.look;
    }


    private void drawFocusedBlock(PGraphics graphics) {
        PVector vector = getSightVector();
        Ray ray = rayCaster.traceRay(this.position, vector, 80);
        PVector position = ray.getHitPostition();
        if (ray.hasTarget()) {
            graphics.pushStyle();
            graphics.stroke(255);
            graphics.strokeWeight(3);
            //graphics.fill(0, 0, 255, 0);
            graphics.translate(0.5f + position.x, 0.5f + position.y, 0.5f + position.z);
            graphics.box(1.01f);
            graphics.popStyle();
        }


    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;

    }


    public void mousePressed() {


        PVector vector = getSightVector();
        Ray ray = rayCaster.traceRay(this.position, vector, 80);
        PVector position = ray.getHitPostition();

        PVector nPlayerPosition = new PVector((int) this.position.x, (int) this.position.y, (int) this.position.z);
        PVector nPlayerPositionPlusOne = new PVector((int) this.position.x, (int) this.position.y + 1, (int) this.position.z);
        PVector normal = ray.getHitNormal();
        PVector nPosition = new PVector((int) ray.getHitPostition().x, (int) ray.getHitPostition().y, (int) ray.getHitPostition().z);
        nPosition.add(normal);
        if (camera.isMouseFocused) {
            if (ray.hasTarget()) {

                if (app.mouseButton == app.LEFT) {
                    world.removeBlock((int) position.x, (int) position.y, (int) position.z, true);
                    Light_Propogation.PropgateStrip(world, (int) position.x, (int) position.z);
                } else if (app.mouseButton == app.RIGHT) {
                    if (!nPlayerPosition.equals(nPosition) && !nPlayerPositionPlusOne.equals(nPosition)) {

                        world.setBlock(BlockManager.BLOCKS[curBlock], (int) position.x + (int) normal.x, (int) position.y + (int) normal.y, (int) position.z + (int) normal.z, true);
                        Light_Propogation.PropgateStrip(world, (int) position.x + (int) normal.x, (int) position.z + (int) normal.z);
                    }
                }
            }
        }

        if (camera.isMouseFocused == false) {
            camera.centerMouse();
            camera.isMouseFocused = true;
        }
    }
}
