package com.bobby.Math;

import com.bobby.Chunk;
import com.bobby.World;
import com.bobby.blocks.Block;
import processing.core.PApplet;
import processing.core.PVector;

public class RayCaster {

    World world;
    PApplet app;

    public RayCaster(PApplet app, World world) {
        this.world = world;
        this.app = app;
    }

//    public Ray fireRay(PVector position, PVector direction, int max_distance) {
//        int m = 8;
//        float x = position.x;
//        float y = position.y;
//        float z = position.z;
//        float dx = direction.x;
//        float dy = direction.y;
//        float dz = direction.z;
//
//        Ray ray = new Ray();
//
//
//        for (int i = 0; i < max_distance * m; i++) {
//            PVector key = new PVector(x, y, z);
//            if (chunk.inBounds(key) && chunk.getBlock((int) key.x, (int) key.y, (int) key.z).isSolid()) {
//                ray.hitPostition = normalizeToBlockSpace(key);
//                ray.hitTarget = true;
//                return ray;
//            }
//
//            x = x + dx / m;
//            y = y + dy / m;
//            z = z + dz / m;
//
//        }
//        ray.hitTarget = false;
//        return ray;
//
//    }

    public PVector normalizeToBlockSpace(PVector position) {

        return new PVector((int) (position.x), (int) (position.y), (int) (position.z));
    }


    public Ray traceRay(PVector position, PVector direction, int maxDistance) {

        // consider raycast vector to be parametrized by t
        //   vec = [px,py,pz] + t * [dx,dy,dz]

        // algo below is as described by this paper:
        // http://www.cse.chalmers.se/edu/year/2010/course/TDA361/grid.pdf

        float px = position.x;
        float py = position.y;
        float pz = position.z;
        float dx = direction.x;
        float dy = direction.y;
        float dz = direction.z;


        float t = 0.0f;
        int ix = (int) px;
        int iy = (int) py;
        int iz = (int) pz;

        int stepx = (dx > 0) ? 1 : -1;
        int stepy = (dy > 0) ? 1 : -1;
        int stepz = (dz > 0) ? 1 : -1;

        // dx,dy,dz are already normalized
        float txDelta = Math.abs(1f / dx);
        float tyDelta = Math.abs(1f / dy);
        float tzDelta = Math.abs(1f / dz);

        float xdist = (stepx > 0) ? (ix + 1 - px) : (px - ix);
        float ydist = (stepy > 0) ? (iy + 1f - py) : (py - iy);
        float zdist = (stepz > 0) ? (iz + 1f - pz) : (pz - iz);

        // location of nearest voxel boundary, in units of t
        float txMax = txDelta * xdist;
        float tyMax = tyDelta * ydist;
        float tzMax = tzDelta * zdist;

        float steppedIndex = -1;

        Ray ray = new Ray();

        // main loop along raycast vector
        while (t <= maxDistance) {

            // exit check

                Block block = this.world.getBlock(ix,iy,iz);
                if(block == null){

                }else {

                    if (this.world.getBlock(ix, iy, iz).getName() != "Air") {
                        ray.hitPostition.x = ix;
                        ray.hitPostition.y = iy;
                        ray.hitPostition.z = iz;

                        if (steppedIndex == 0) ray.hitNormal.x = -stepx;
                        if (steppedIndex == 1) ray.hitNormal.y = -stepy;
                        if (steppedIndex == 2) ray.hitNormal.z = -stepz;
                        ray.hitTarget = true;
                        return ray;
                    }
                }
                // advance t to next nearest voxel boundary
                if (txMax < tyMax) {
                    if (txMax < tzMax) {
                        ix += stepx;
                        t = txMax;
                        txMax += txDelta;
                        steppedIndex = 0;
                    } else {
                        iz += stepz;
                        t = tzMax;
                        tzMax += tzDelta;
                        steppedIndex = 2;
                    }
                } else {
                    if (tyMax < tzMax) {
                        iy += stepy;
                        t = tyMax;
                        tyMax += tyDelta;
                        steppedIndex = 1;
                    } else {
                        iz += stepz;
                        t = tzMax;
                        tzMax += tzDelta;
                        steppedIndex = 2;
                    }
                }
            }


        ray.hitTarget = false;
        return ray;

    }
}
