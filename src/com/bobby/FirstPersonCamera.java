package com.bobby;

import com.jogamp.newt.opengl.GLWindow;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
import java.util.HashMap;

public class FirstPersonCamera {
    public PVector position;
    PApplet app;

    HashMap<Character, Boolean> keys;
    private PVector up;
    private PVector right;
    public PVector forward;
    public PVector look;
    public PVector velocity;
    private PVector center;
    private Point mouse;
    private Point prevMouse;

    float sensitivity = 0.15f;

    public Robot robot;

    public float tilt, pan;

    private float friction = 0.95f;

    public boolean isMouseFocused = true;



    float speed;
    public FirstPersonCamera(PApplet app){
        this.app = app;
        this.position = new PVector(0,0,0);

        try {
            robot = new Robot();
        } catch (Exception e){}

        this.keys = new HashMap<Character, Boolean>();

        position = new PVector(0f, 0f, 0f);
        up = new PVector(0f, 1f, 0f);
        right = new PVector(1f, 0f, 0f);
        forward = new PVector(0f, 0f, 1f);
        look = new PVector(0f, 0.5f, 1f);
        velocity = new PVector(0f, 0f, 0f);
        pan = 0;
        tilt = 0f;

        app.perspective(PConstants.PI/3f, (float)app.width/(float)app.height, 0.01f, 1000f);

    }

    public void centerMouse(){
        int x = ((GLWindow) app.getSurface().getNative()).getX();
        int y = ((GLWindow) app.getSurface().getNative()).getY();
        int w = app.width;
        int h = app.height;

        robot.mouseMove(w / 2 + x, h / 2 + y);
    }

    public void update(){
        speed = 3.5f * (1.0f / app.frameRate);
        mouse = MouseInfo.getPointerInfo().getLocation();
        if (prevMouse == null) prevMouse = new Point(mouse.x, mouse.y);
        app.cursor();
        //int x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().x;
        if(isMouseFocused) {
            int x = ((GLWindow) app.getSurface().getNative()).getX();
            int y = ((GLWindow) app.getSurface().getNative()).getY();
            int w = app.width;
            int h = app.height;

            int middleX = w / 2 + x;
            int middleY = h / 2 + y;

            int deltaX = mouse.x - middleX;
            int deltaY = mouse.y - middleY;

            app.noCursor();

            centerMouse();

            pan += PApplet.map(deltaX, 0, app.width, 0, PConstants.TWO_PI) * sensitivity;
            tilt += PApplet.map(deltaY, 0, app.height, 0, PConstants.PI) * sensitivity;
        }
        tilt = clamp(tilt, -PConstants.PI / 2.01f, PConstants.PI / 2.01f);


        if (tilt == PConstants.PI/2) tilt += 0.001f;

        forward = new PVector(PApplet.cos(pan), 0, PApplet.sin(pan));
        forward.normalize();
        look = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));
        look.normalize();
        right = new PVector(PApplet.cos(pan - PConstants.PI/2), 0, PApplet.sin(pan - PConstants.PI/2));
        right.normalize();
        prevMouse = new Point(mouse.x, mouse.y);

        if (keys.containsKey('a') && keys.get('a')) position.add(PVector.mult(right, speed));
        if (keys.containsKey('d') && keys.get('d')) position.sub(PVector.mult(right, speed));
        if (keys.containsKey('w') && keys.get('w')) position.add(PVector.mult(forward, speed));
        if (keys.containsKey('s') && keys.get('s')) position.sub(PVector.mult(forward, speed));
        if (keys.containsKey('q') && keys.get('q')) velocity.add(PVector.mult(up, speed * 0.1f));
        if (keys.containsKey('e') && keys.get('e')) velocity.sub(PVector.mult(up, speed * 0.1f));

        //velocity.mult(friction);
        position.add(velocity);
        center = PVector.add(position, look);
    }

    public void draw(){

        app.camera(position.x, position.y + 0.5f, position.z, center.x, center.y + 0.5f, center.z, up.x, up.y, up.z);

    }




    public void keyPressed(){
        char key = app.key;
        keys.put(Character.toLowerCase(key), true);
    }

    public void keyReleased(){
        char key = app.key;
        keys.put(Character.toLowerCase(key), false);
    }

    public float clamp(float x, float min, float max){
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    public PVector getPosition() {
        return position;
    }
}
