package com.bobby;

import processing.core.PApplet;

public class MemoryProfiler {

    Runtime rt;

    long usedMB;

    public MemoryProfiler(){
        rt = Runtime.getRuntime();

    }

    public void draw(PApplet applet, int x, int y){
        usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        applet.fill(255,150,0);
        applet.textAlign(applet.LEFT, applet.TOP);
        applet.textFont(((Main)applet).font, 22);
        applet.text("Memory Used: " + usedMB + "MB", x,y);
    }

}
