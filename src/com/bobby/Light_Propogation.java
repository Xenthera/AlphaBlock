package com.bobby;

import processing.core.PVector;

import java.util.ArrayList;

public class Light_Propogation {

    private static ArrayList<PVector> light_queue;

    static {
        light_queue = new ArrayList<>();
    }
    
    public static void PropogateWorld(World world){

        int chunkCount = 0;
        for (int x = 0; x < world.x_blocks; x++) {
            for (int z = 0; z < world.z_blocks; z++) {

                int natural = 15;

                for (int y = 0; y < world.y_blocks; y++) {

                    world.getBlock(x,y,z).lightLevel = natural;

                    if(world.getBlock(x,y,z).isOpaque() || world.getBlock(x,y,z).getName() == "Leaves"){
                        natural = 0;
                    }


                }
                chunkCount++;
                world.progressType = "Generating Lighting: " + chunkCount + "/" + (world.x_blocks *  world.z_blocks);
                world.progress = chunkCount / (world.x_blocks * world.z_blocks);
            }
        }

        chunkCount = 0;
        for (int x = 0; x < world.x_blocks; x++) {
            for (int z = 0; z < world.z_blocks; z++) {
                for (int y = 0; y < world.y_blocks; y++) {

                    if(world.getBlock(x,y,z).lightLevel == 0){

                        if(     world.getBlock(x + 1, y, z).lightLevel == 15 ||
                                world.getBlock(x - 1, y, z).lightLevel == 15 ||

                                world.getBlock(x, y + 1, z).lightLevel == 15 ||
                                world.getBlock(x, y - 1, z).lightLevel == 15 ||

                                world.getBlock(x, y, z + 1).lightLevel == 15 ||
                                world.getBlock(x, y, z - 1).lightLevel == 15)
                        {
                            world.getBlock(x,y,z).lightLevel = 14;
                            light_queue.add(new PVector(x,y,z));
                        }
                    }
                    chunkCount++;
                    world.progressType = "Propagating Light: " + chunkCount + "/" + (world.x_blocks * world.y_blocks * world.z_blocks);
                    world.progress = chunkCount / (world.x_blocks * world.y_blocks * world.z_blocks);
                }
            }
        }
        world.progressType = "Resolving Light Queue";
        Resolve_Light(world);

    }

    public static void PropgateStrip(World world, int x, int z){

        int natural = 15;

        for (int y = 0; y < world.y_blocks; y++) {

            world.getBlock(x,y,z).lightLevel = natural;

            if(world.getBlock(x,y,z).isOpaque() || world.getBlock(x,y,z).getName() == "Leaves"){
                natural = 0;
            }
            light_queue.add(new PVector(x, y, z));

            light_queue.add(new PVector(x - 1, y, z));
            light_queue.add(new PVector(x + 1, y, z));

            light_queue.add(new PVector(x, y, z - 1));
            light_queue.add(new PVector(x, y, z + 1));
        }

        Resolve_Light(world);

    }

    private static void Resolve_Light(World world){

        while(light_queue.size() > 0){
            int x = (int)light_queue.get(light_queue.size() - 1).x;
            int y = (int)light_queue.get(light_queue.size() - 1).y;
            int z = (int)light_queue.get(light_queue.size() - 1).z;

            light_queue.remove(light_queue.size() - 1);

            int current_value = world.getBlock(x,y,z).lightLevel;

            if(world.inBounds(x + 1,y,z) && world.getBlock(x + 1,y,z).lightLevel < current_value - 1){
                world.getBlock(x + 1, y, z).lightLevel = current_value-1;
                light_queue.add(new PVector(x + 1, y, z));
            }
            if(world.inBounds(x - 1,y,z) && world.getBlock(x - 1,y,z).lightLevel < current_value - 1){
                world.getBlock(x - 1, y, z).lightLevel = current_value-1;
                light_queue.add(new PVector(x - 1, y, z));
            }

            if(world.inBounds(x, y + 1, z) && world.getBlock(x, y + 1, z).lightLevel < current_value - 1){
                world.getBlock(x, y + 1, z).lightLevel = current_value-1;
                light_queue.add(new PVector(x, y + 1, z));
            }
            if(world.inBounds(x, y - 1, z) && world.getBlock(x, y - 1, z).lightLevel < current_value - 1){
                world.getBlock(x, y - 1, z).lightLevel = current_value-1;
                light_queue.add(new PVector(x, y - 1, z));
            }

            if(world.inBounds(x, y, z + 1) && world.getBlock(x, y, z + 1).lightLevel < current_value - 1){
                world.getBlock(x, y, z + 1).lightLevel = current_value-1;
                light_queue.add(new PVector(x, y, z + 1));
            }
            if(world.inBounds(x, y, z - 1) && world.getBlock(x,y,z-1).lightLevel < current_value - 1){
                world.getBlock(x, y, z - 1).lightLevel = current_value-1;
                light_queue.add(new PVector(x, y, z - 1));
            }
        }

    }

}
