package com.bobby;

import com.bobby.blocks.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class World {

    PApplet applet;

    public float gravity = 0.35f;

    ArrayList<Chunk> chunks;
    //Temporary hard-coded values
    int chunkWidth = 16;
    int chunkLength = 16;
    boolean isLoaded = false;
    boolean isLoading = false;

    float progress = 0;
    String progressType = "";


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
        this.isLoading = true;
        chunks = new ArrayList<>();

        float chunkCount = 0;
        this.progressType = "Creating chunks";
        for (int x = 0; x < chunkWidth; x++) {
            for (int z = 0; z < chunkLength; z++) {
                Chunk chunk = new Chunk(applet, this, x, z);
                chunks.add(chunk);
                chunkCount++;
                this.progress = chunkCount / (chunkWidth * chunkLength);
            }
        }
        chunkCount = 0;
        this.progressType = "Generating terrain";
        for(Chunk chunk : this.chunks){

            chunkCount++;
            this.progress = chunkCount / (chunkWidth * chunkLength);

            for (int i = 0; i < chunk.CHUNK_WIDTH; i++) {
                for (int j = 0; j < chunk.CHUNK_LENGTH; j++) {
                    int yTop = (int) (applet.noise((chunk.position.x * 16 + i) * 0.02f,
                            (chunk.position.z * 16 + j) * 0.02f) * 45) + 70;
                    for (int k = yTop + 1; k < chunk.CHUNK_HEIGHT; k++) {
                        this.setBlock(new BlockDirt(), i + (int)chunk.position.x * chunk.CHUNK_WIDTH, k, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
                    }


                    if (yTop > 90) {
                        this.setBlock(new BlockSand(), i + (int)chunk.position.x * chunk.CHUNK_WIDTH, yTop, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
                    } else {
                        this.setBlock(new BlockGrass(),i + (int)chunk.position.x * chunk.CHUNK_WIDTH, yTop, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
                    }

                    if(applet.random(1) > 0.985f &&!(getBlock(i + (int)chunk.position.x * chunk.CHUNK_WIDTH, yTop, j + (int)chunk.position.z * chunk.CHUNK_LENGTH).getName().equals("Sand"))){
                        chunk.createTree(i, yTop, j);
                    }


                }
            }
        }

        int topLayerDepth = 1;

        chunkCount = 0;
        this.progressType = "Constructing chunks";
        for (int i = 0; i < chunkWidth; i++) {
            for (int j = 0; j < chunkLength; j++) {
                chunks.get(i * chunkWidth + j).regenerate();
                chunkCount++;
                this.progress = chunkCount / (chunkWidth * chunkLength);
            }
        }

        this.isLoaded = true;
        this.isLoading = false;
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
