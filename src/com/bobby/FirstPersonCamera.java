package com.bobby;

import com.jogamp.newt.opengl.GLWindow;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.*;

public class FirstPersonCamera {

    PApplet app;


    public PVector up;
    public PVector right;
    public PVector forward;
    public PVector look;
    private PVector center;
    private Point mouse;
    private Point prevMouse;

    Player player;

    float sensitivity = 0.15f;

    public Robot robot;

    public float tilt, pan;



    public boolean isMouseFocused = true;



    public FirstPersonCamera(PApplet app, Player player){
        this.app = app;


        this.player = player;

        try {
            robot = new Robot();
        } catch (Exception e){}

        up = new PVector(0f, 1f, 0f);
        right = new PVector(1f, 0f, 0f);
        forward = new PVector(0f, 0f, 1f);
        look = new PVector(0f, 0.5f, 1f);

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
        mouse = MouseInfo.getPointerInfo().getLocation();
        if (prevMouse == null) prevMouse = new Point(mouse.x, mouse.y);
        app.cursor();
        if(isMouseFocused && app.focused) {
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

        tilt = player.clamp(tilt, -PConstants.PI / 2.01f, PConstants.PI / 2.01f);


        if (tilt == PConstants.PI/2) tilt += 0.001f;

        forward = new PVector(PApplet.cos(pan), 0, PApplet.sin(pan));
        forward.normalize();
        look = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));
        look.normalize();
        right = new PVector(PApplet.cos(pan - PConstants.PI/2), 0, PApplet.sin(pan - PConstants.PI/2));
        right.normalize();
        prevMouse = new Point(mouse.x, mouse.y);
        center = PVector.add(player.position, look);

    }

    public void draw(PGraphics graphics){
        graphics.camera(this.player.position.x, this.player.position.y, this.player.position.z, center.x, center.y, center.z, up.x, up.y, up.z);
    }








}
