package com.bobby.Math;

import processing.core.PVector;

public class Ray {
    public PVector hitPostition;
    public PVector hitNormal;

    boolean hitTarget = false;

    public Ray(){
        this.hitPostition = new PVector(0,0,0);
        this.hitNormal = new PVector(0,0,0);
    }



    public boolean hasTarget(){
        return this.hitTarget;
    }

    public PVector getHitPostition() {
        return hitPostition;
    }

    public PVector getHitNormal() {
        return hitNormal;
    }
}
