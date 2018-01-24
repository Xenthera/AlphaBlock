package com.bobby;

import com.bobby.blocks.Block;
import com.bobby.blocks.BlockAir;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class World {

    PApplet applet;

    ArrayList<Chunk> chunks;
    //Temporary hard-coded values
    int chunkWidth = 14;
    int chunkLength = 14;
    boolean isLoaded = false;


    public World(PApplet applet){
        this.applet = applet;



    }

    public PVector getRandomSpawnPoint(){
        int randX = (int)applet.random(16 * chunkWidth) - 1;
        int randZ = (int)applet.random(16 * chunkLength) - 1;
        int randY = 0;
        for (int i = 0; i < 128; i++) {
            if(this.getBlock(randX, i, randZ).isSolid()){
                randY = i;
                break;
            }
        }

        return new PVector(randX, randY, randZ);
    }

    public void load(){
        chunks = new ArrayList<>();

        for (int i = 0; i < chunkWidth; i++) {
            for (int j = 0; j < chunkLength; j++) {
                chunks.add(new Chunk(applet, this, i, j));
            }
        }
        for (int i = 0; i < chunkWidth; i++) {
            for (int j = 0; j < chunkLength; j++) {
                chunks.get(i * chunkWidth + j).regenerate();
            }
        }

        this.isLoaded = true;
    }

    public void draw(){
        for (int i = 0; i < chunkWidth; i++) {
            for (int j = 0; j < chunkLength; j++) {
                applet.pushMatrix();
                applet.translate(i * 16, 0, j * 16);
                chunks.get(i * chunkWidth + j).draw();
                applet.popMatrix();
            }
        }
    }

    public Chunk getChunk(int chunkX, int chunkZ){
        return chunks.get(chunkX * chunkWidth + chunkZ);
    }

    public Block getBlock(int x, int y, int z) {
        if(x < 0 || y < 0 || z < 0 || x > chunkWidth * 16 - 1 || y > 128 - 1 || z > chunkLength * 16 - 1){
            return new BlockAir();
        }

        int chunkX = x / 16;
        int blockX = x % 16;
        int chunkZ = z / 16;
        int blockZ = z % 16;

        return chunks.get(chunkX * chunkWidth + chunkZ).getBlock(blockX, y, blockZ);

    }



    public void setBlock(Block block, int x, int y, int z, boolean setByUser) {
        if(x < 0 || y < 0 || z < 0 || x > chunkWidth * 16 - 1 || y > 128 - 1 || z > chunkLength * 16 - 1){
            return;
        }
        int chunkX = x / 16;
        int blockX = x % 16;
        int chunkZ = z / 16;
        int blockZ = z % 16;

        chunks.get(chunkX * chunkWidth + chunkZ).setBlock(block, blockX, y, blockZ, setByUser);
    }

    public void removeBlock(int x, int y, int z, boolean setByUser) {
        this.setBlock(new BlockAir(), x, y, z, setByUser);
    }


}
