package com.bobby;

import com.bobby.blocks.*;
import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;

public class Chunk {

    PApplet applet;

    PShape meshes[];

    public TextureManager textureManager;

    public final int CHUNK_WIDTH = 16, CHUNK_LENGTH = 16, CHUNK_HEIGHT = 128;
    private final int cwidth = 16, clength = 16, cheight = 16;
    private final int chunkStackHeight = CHUNK_HEIGHT / cheight; // 16 * 8 = 128;
    private final int MAX_CHUNK_UPDATES = 8; // per frame

    ArrayList<Block[][][]> subChunks;

    boolean[] subChunkDirtyList; // Flag to check if subchunk needs rebuilding


    public Chunk(PApplet applet) {

        this.applet = applet;

        textureManager = new TextureManager(this.applet);

        subChunks = new ArrayList();
        meshes = new PShape[chunkStackHeight];
        subChunkDirtyList = new boolean[chunkStackHeight];

        //Initialize Everything
        for (int i = 0; i < chunkStackHeight; i++) {
            Block[][][] subChunk = new Block[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_LENGTH];
            subChunks.add(subChunk);
            meshes[i] = this.applet.createShape();
            subChunkDirtyList[i] = true;
        }

        //TEMPORARY: Generate full chunk
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_LENGTH; z++) {
                    Block block;;
                    int r = (int)applet.random(4);
                    switch (r){
                        case 0:
                            block = new BlockStoneBrick();
                            break;
                        case 1:
                            block = new BlockStone();
                            break;
                        case 2:
                            block = new BlockDirt();
                            break;
                        default:
                            block = new BlockAir();

                    }

                    //block.setLightLevel((int)random(15));
                    setBlock(block, x, y, z);
                }
            }
        }







        regenerate();
    }

    public Block getBlock(int x, int y, int z) {
        if(!inBounds(x,y,z))
            return null;
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        Block[][][] blocks = subChunks.get(chunkLocation);

        return blocks[x][blockLocation][z];
    }

    public void setBlock(Block block, int x, int y, int z) {
        if(!inBounds(x,y,z)){
            return;
        }
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        Block[][][] blocks = subChunks.get(chunkLocation);

        blocks[x][blockLocation][z] = block;

        //Determine if block in subChunk is neighboring another chunk and rebuild it too...
        if (blockLocation == cheight - 1 && chunkLocation < chunkStackHeight - 1) {
            subChunkDirtyList[chunkLocation + 1] = true;
        }
        if (blockLocation == 0 && chunkLocation > 0) {
            subChunkDirtyList[chunkLocation - 1] = true;
        }
        if (subChunkDirtyList[chunkLocation] == false) {
            subChunkDirtyList[chunkLocation] = true;
        }
    }

    public void removeBlock(int x, int y, int z) {
        this.setBlock(new BlockAir(), x, y, z);
    }

    public void regenerate() {
        int updateCount = 0;
        boolean faceDefault = true;
        for (int i = 0; i < chunkStackHeight; i++) {
            if (subChunkDirtyList[i] == true && updateCount < MAX_CHUNK_UPDATES) { // If the chunk is dirty?
                updateCount++;
                meshes[i] = null;
                meshes[i] = this.applet.createShape();
                meshes[i].beginShape(this.applet.TRIANGLE);
                meshes[i].texture(textureManager.getTextureAtlas());


                meshes[i].noStroke();
                for (int x = 0; x < cwidth; x++) {
                    for (int y = 0 + (i * cheight); y < cheight + (i * cheight); y++) {
                        for (int z = 0; z < clength; z++) {

                            if (getBlock(x, y, z).isSolid() == false) {
                                continue;
                            }
                            boolean nx = faceDefault;
                            if (x > 0) {
                                nx = !getBlock(x-1, y, z).isSolid();
                            }
                            boolean px = faceDefault;
                            if (x < CHUNK_WIDTH - 1) {
                                px = !getBlock(x+1, y, z).isSolid();
                            }
                            boolean ny = faceDefault;
                            if (y > 0) {
                                ny = !getBlock(x, y-1, z).isSolid();
                            }
                            boolean py = faceDefault;
                            if (y < CHUNK_HEIGHT - 1) {
                                py = !getBlock(x, y+1, z).isSolid();
                            }
                            boolean nz = faceDefault;
                            if (z > 0) {
                                nz = !getBlock(x, y, z-1).isSolid();
                            }
                            boolean pz = faceDefault;
                            if (z < CHUNK_LENGTH - 1) {
                                pz = !getBlock(x, y, z+1).isSolid();
                            }

                            meshes[i].tint(this.applet.map(getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));
                            BlockGeometry.constructBlock(textureManager, getBlock(x, y, z), meshes[i], nx, px, ny, py, nz, pz, x, y, z);

                            }
                    }
                }
                meshes[i].endShape();
                subChunkDirtyList[i] = false; // All clean!
            }
        }
    }

    private boolean isDirty() {
        for (int i = 0; i <  subChunkDirtyList.length; i++) {
            if (subChunkDirtyList[i] == true) {
                return true;
            }
        }
        return false;
    }

    public boolean inBounds(int x, int y, int z){
        if(!(x >= 0 && x < this.CHUNK_WIDTH)){
            return false;
        }
        if(!(y >= 0 && y < this.CHUNK_HEIGHT)){
            return false;
        }
        if(!(z >= 0 && z < this.CHUNK_LENGTH)){
            return false;
        }
        return true;
    }



    public void draw() {
        if (isDirty())
            regenerate();
        for (PShape mesh : meshes) {
            this.applet.shape(mesh);
        }
    }
}