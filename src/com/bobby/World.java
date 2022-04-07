package com.bobby;

import com.bobby.FastNoise.FastNoise;
import com.bobby.blocks.*;
import com.bobby.blocks.BlockBedrock;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {



    PApplet applet;

    public float gravity = 0.35f;

    ConcurrentHashMap<Tuple, Chunk> chunks;
    //Temporary hard-coded values
    int chunkWidth = 32;
    int chunkLength = 32;
    boolean isLoaded = false;
    boolean isLoading = false;
    int x_blocks = chunkWidth * 16;
    int z_blocks = chunkLength * 16;
    int y_blocks = 256;

    public TextureManager textureManager;

    float progress = 0;
    String progressType = "";


    public World(PApplet applet){
        this.applet = applet;
        this.textureManager = new TextureManager(applet);
    }

    public PVector getRandomSpawnPoint(){



        return new PVector(0,200,0);
    }

    public void createTree(int x, int y, int z){
        int height;
        float type = applet.random(1f);

        height = (int) applet.random(4, 8);

        if(applet.random(1) > 0.4) {

            for (int k = 0; k < height; k++) {
                if (k < height / 2) {
                    if (type >= 0.5f && k % 2 == 0) {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                this.setBlock(Blocks.OAK_LEAVES, x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    } else {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                this.setBlock(Blocks.OAK_LEAVES, x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    }
                }
                this.setBlock(Blocks.OAK, x, y - (height - 1) + k, z, false);
            }
            this.setBlock(Blocks.OAK_LEAVES, x, y - height - 1, z, false);
        }else{
            for (int k = 0; k < height; k++) {
                if (k < height / 2) {
                    if (type >= 0.5f && k % 2 == 0) {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                    this.setBlock(Blocks.BIRCH_LEAVES, x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    } else {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                this.setBlock(Blocks.BIRCH_LEAVES, x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    }
                }
                this.setBlock(Blocks.BIRCH, x, y - (height - 1) + k, z, false);
            }
            this.setBlock(Blocks.BIRCH_LEAVES, x, y - height - 1, z, false);
        }
    }

    public void load(){



        //this.isLoading = true;
        chunks = new ConcurrentHashMap<>();

        float chunkCount = 0;
        this.progressType = "Creating chunks";
        for (int x = 0; x < chunkWidth; x++) {
            for (int z = 0; z < chunkLength; z++) {
                Chunk chunk = new Chunk(applet, this, x, z);
                chunks.put(new Tuple(x, z), chunk);

                

                chunkCount++;
            }
        }
        for (Map.Entry<Tuple, Chunk> set : chunks.entrySet()) {
            try {
                Thread.sleep(1000/20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            set.getValue().regenerate();
        }

        chunkCount = 0;
        float frequency = 4.0f;


//        chunkCount = 0;
//        for (int x = 0; x < this.x_blocks; x++) {
//            for (int z = 0; z < this.z_blocks; z++) {
//                for (int y = 0; y < this.y_blocks; y++) {
//
//                    if(this.getBlock(x,y,z) != Blocks.AIR){
//                        if(y / ((float)this.y_blocks) > 0.65f){
//                            if(fastNoise.GetValueFractal(x * frequency * 5, z * frequency * 5) >= -0.2){
//                                this.setBlock(Blocks.SAND, x,y,z,false);
//                                this.setBlock(Blocks.SANDSTONE, x,y + 1,z,false);
//                                this.setBlock(Blocks.SANDSTONE, x,y + 2,z,false);
//                                this.setBlock(Blocks.SANDSTONE, x,y + 3,z,false);
//                            }else{
//                                this.setBlock(Blocks.GRAVEL, x,y,z,false);
//                                this.setBlock(Blocks.GRAVEL, x,y + 1,z,false);
//                                this.setBlock(Blocks.GRAVEL, x,y + 2,z,false);
//                                this.setBlock(Blocks.GRAVEL, x,y + 3,z,false);
//                            }
//                        }else{
//                            this.setBlock(Blocks.GRASS, x,y,z, false);
//                            this.setBlock(Blocks.DIRT, x,y + 1,z,false);
//                            this.setBlock(Blocks.DIRT, x,y + 2,z,false);
//                            this.setBlock(Blocks.DIRT, x,y + 3,z,false);
//                            if(applet.random(1) < 0.4) {
//                                this.setBlock(Blocks.PLANT_GRASS, x, y - 1, z, false);
//                            }
//                        }
//                        break;
//                    }
//
//
//                    chunkCount++;
//                    this.progressType = "Decorating Terrain: " + (int)chunkCount + "/" + (x_blocks * y_blocks * z_blocks);
//                    this.progress = chunkCount / (x_blocks * y_blocks * z_blocks);
//                }
//            }
//        }
//        for (int x = 0; x < this.x_blocks; x++) {
//            for (int z = 0; z < this.z_blocks; z++) {
//                for (int y = 0; y < this.y_blocks; y++) {
//
//                    if(this.getBlock(x,y,z) != Blocks.AIR){
//                        if(applet.random(1) > 0.895f && (getBlock(x,y,z) == Blocks.GRASS)){
//                            this.createTree(x ,y ,z);
//                            if(applet.random(1) > 0.5) {
//                                this.setBlock(Blocks.FLOWER, x, y - 1, z, false);
//                            }else{
//                                this.createTree(x,y,z);
//                            }
//                        }else if(applet.random(1) > 0.98 && (getBlock(x,y,z) == Blocks.SAND)){
//                            if(applet.random(1) < 0.15) {
//                                int rand = (int) applet.random(5);
//                                for (int i = 1; i <= rand + 1; i++) {
//                                    this.setBlock(Blocks.REED, x, y - i, z, false);
//                                }
//                            }else{
//                                this.setBlock(Blocks.DEAD_SHRUB, x, y - 1, z, false);
//                            }
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//
//        for (int x = 0; x < this.x_blocks; x++) {
//            for (int z = 0; z < this.z_blocks; z++) {
//                for (int y = 0; y < this.y_blocks; y++) {
//
//                    if(this.getBlock(x,y,z) == Blocks.AIR && y > 170){
//                        this.setBlock(Blocks.WATER, x, y, z, false);
//
//                    }
//                }
//            }
//        }

        //Light_Propogation.PropogateWorld(this);

//        for(Chunk chunk : this.chunks){
//
//            chunkCount++;
//            this.progress = chunkCount / (chunkWidth * chunkLength);
//
//            for (int i = 0; i < chunk.CHUNK_WIDTH; i++) {
//                for (int j = 0; j < chunk.CHUNK_LENGTH; j++) {
//                    int yTop = (int) (applet.noise((chunk.position.x * 16 + i) * 0.02f,
//                            (chunk.position.z * 16 + j) * 0.02f) * 45) + 70;
//                    for (int k = yTop + 1; k < chunk.CHUNK_HEIGHT; k++) {
//                        this.setBlock(new BlockDirt(), i + (int)chunk.position.x * chunk.CHUNK_WIDTH, k, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
//                    }
//
//                    if (yTop > 90) {
//                        this.setBlock(new BlockSand(), i + (int)chunk.position.x * chunk.CHUNK_WIDTH, yTop, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
//                    } else {
//                        this.setBlock(new BlockGrass(),i + (int)chunk.position.x * chunk.CHUNK_WIDTH, yTop, j + (int)chunk.position.z * chunk.CHUNK_LENGTH, false);
//                    }
//

//
//
//                }
//            }
//        }


        int topLayerDepth = 1;



    }

    public void draw(PGraphics graphics, PShader normalShader, PShader auxShader){

        for (Map.Entry<Tuple, Chunk> set : chunks.entrySet()) {

            Chunk chunk = set.getValue();


            graphics.pushMatrix();
            graphics.translate(chunk.position.x * 16, 0, chunk.position.y * 16);
            chunk.draw(graphics, normalShader, auxShader);
            graphics.popMatrix();
        }
    }

    public Chunk getChunk(int chunkX, int chunkZ){
        Chunk c = chunks.get(new Tuple(chunkX, chunkZ));
        return c;
    }

    public boolean inBounds(int x, int y, int z){
        return !(x < 0 || y < 0 || z < 0 || x > chunkWidth * 16 - 1 || y > 256 - 1 || z > chunkLength * 16 - 1);
    }

    public int getBlock(int x, int y, int z) {
        if(!this.inBounds(x,y,z)){
            return Blocks.AIR;
        }

        int chunkX = x / 16;
        int blockX = x % 16;
        int chunkZ = z / 16;
        int blockZ = z % 16;

        int chunkIndex = chunkX * chunkWidth + chunkZ;

        Chunk c = getChunk(chunkX, chunkZ);

        if(c != null) {
            return c.getBlock(blockX, y, blockZ);
        }else{
            return Blocks.AIR;
        }

    }



    public void setBlock(short block, int x, int y, int z, boolean setByUser) {
        if(!this.inBounds(x,y,z)){
            return;
        }
        int chunkX = x / 16;
        int blockX = x % 16;
        int chunkZ = z / 16;
        int blockZ = z % 16;

        Chunk c = getChunk(chunkX, chunkZ);

        if(c != null){
            c.setBlock(block, blockX, y, blockZ, setByUser);
        }
    }

    public void removeBlock(int x, int y, int z, boolean setByUser) {
        this.setBlock(Blocks.AIR, x, y, z, setByUser);
    }


}
