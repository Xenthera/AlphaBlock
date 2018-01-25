package com.bobby;

import com.bobby.blocks.*;
import com.bobby.blocks.construction.BlockGeometry;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

public class Chunk {

    private PApplet applet;

    private PShape meshes[];

    public TextureManager textureManager;

    public PVector position;

    private World world;

    public final int CHUNK_WIDTH = 16, CHUNK_LENGTH = 16, CHUNK_HEIGHT = 128;
    private final int cwidth = 16, clength = 16, cheight = 16;
    private final int chunkStackHeight = CHUNK_HEIGHT / cheight; // 16 * 8 = 128;

    ArrayList<Block[][][]> subChunks;

    boolean[] subChunkDirtyList; // Flag to check if subchunk needs rebuilding


    public Chunk(PApplet applet, World world, int x, int y) {
        this.world = world;
        this.position = new PVector(x, 0, y);


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

        for (int i = 0; i < CHUNK_WIDTH; i++) {
            for (int j = 0; j < CHUNK_LENGTH; j++) {
                for (int k = 0; k < CHUNK_HEIGHT; k++) {
                    setBlock(new BlockAir(), i, k, j, false);
                }

            }
        }

        //TEMPORARY: Generate full chunk
        int topLayerDepth = 2;

        for (int i = 0; i < CHUNK_WIDTH; i++) {
            for (int j = 0; j < CHUNK_LENGTH; j++) {
                int yTop = (int) (applet.noise((this.position.x * 16 + i) * 0.02f,
                        (this.position.z * 16 + j) * 0.02f) * 127) + 1;
                for (int k = yTop + 1; k < CHUNK_HEIGHT; k++) {
                    setBlock(new BlockDirt(), i, k, j, false);
                }
                for (int k = 0; k < topLayerDepth; k++) {

                    if (yTop > 80) {
                        setBlock(new BlockSand(), i, yTop + k, j, false);
                    } else {
                        setBlock(new BlockGrass(), i, yTop + k, j, false);
                    }

                    if(applet.random(1) > 0.9999f){
                        createPillar(i, yTop, j);
                    }

                }
            }
        }
    }

    private void createPillar(int x, int y, int z){
        int height = 16;
        for (int k = 0; k < height; k++) {
            setBlock(new BlockStoneBrick(), x, y - height + k, z, false);
            setBlock(new BlockStoneBrick(), x + 1, y - height + k, z, false);
            setBlock(new BlockStoneBrick(), x, y - height + k, z + 1, false);
            setBlock(new BlockStoneBrick(), x + 1, y - height + k, z + 1, false);
        }
    }

    public Block getBlock(int x, int y, int z) {
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        Block[][][] blocks = subChunks.get(chunkLocation);

        return blocks[x][blockLocation][z];
    }

    public void setBlock(Block block, int x, int y, int z, boolean setByUser) {
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        if(x < 0 || y < 0 || z < 0 || x > cwidth - 1 || y > 128 - 1 || z > clength - 1){
            return;
        }

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
        PVector worldSpace = toWorldSpace(x,y,z);
        // If the block was placed by a user, update the neighboring chunks if on the edge of a chunk.
        if(setByUser) {
            if (x == 0) {
                if (world.getBlock((int) worldSpace.x - 1, y, (int) worldSpace.z).isSolid()) {
                    world.getChunk((int)this.position.x - 1, (int)this.position.z).subChunkDirtyList[chunkLocation] = true;
                }
            }
            if (x == cwidth - 1) {
                if (world.getBlock((int) worldSpace.x + 1, y, (int) worldSpace.z).isSolid()) {
                    world.getChunk((int)this.position.x + 1, (int)this.position.z).subChunkDirtyList[chunkLocation] = true;
                    applet.println("X positive edge");
                }
            }
            if (z == 0) {
                if (world.getBlock((int) worldSpace.x, y, (int) worldSpace.z - 1).isSolid()) {
                    world.getChunk((int)this.position.x, (int)this.position.z - 1).subChunkDirtyList[chunkLocation] = true;
                }
            }
            if (z == clength - 1) {
                if (world.getBlock((int) worldSpace.x, y, (int) worldSpace.z + 1).isSolid()) {
                    world.getChunk((int)this.position.x, (int)this.position.z + 1).subChunkDirtyList[chunkLocation] = true;
                }
            }
        }
    }

    public PVector toWorldSpace(int x, int y, int z){
        return new PVector((int)(x + (this.position.x * cwidth)), y, (int)(z + (this.position.z * cwidth)));
    }

    public void removeBlock(int x, int y, int z, boolean setByUser) {
        this.setBlock(new BlockAir(), x, y, z, setByUser);
    }

    public void regenerate() {
        boolean faceDefault = true;
        for (int i = 0; i < chunkStackHeight; i++) {
            if (subChunkDirtyList[i] == true) { // If the chunk is dirty?
                meshes[i] = null;
                meshes[i] = this.applet.createShape();
                meshes[i].beginShape(this.applet.TRIANGLE);
                meshes[i].texture(textureManager.getTextureAtlas());
                meshes[i].noStroke();
                for (int x = 0; x < cwidth; x++) {
                    for (int y = 0 + (i * cheight); y < cheight + (i * cheight); y++) {
                        for (int z = 0; z < clength; z++) {

                            PVector worldSpace = toWorldSpace(x,y,z);
                            int worldX = (int)worldSpace.x;
                            int worldZ = (int)worldSpace.z;

                            if (world.getBlock(worldX, y, worldZ).isSolid() == false) {
                                continue;
                            }
                            boolean nx = faceDefault;
                            if (worldX > 0) {
                                nx = !world.getBlock(worldX - 1, y, worldZ).isSolid();
                            }
                            boolean px = faceDefault;
                            if (worldX < world.chunkWidth * CHUNK_WIDTH - 1) {
                                px = !world.getBlock(worldX + 1, y, worldZ).isSolid();
                            }
                            boolean ny = faceDefault;
                            if (y > 0) {
                                ny = !world.getBlock(worldX, y - 1, worldZ).isSolid();
                            }
                            boolean py = faceDefault;
                            if (y < CHUNK_HEIGHT - 1) {
                                py = !world.getBlock(worldX, y + 1, worldZ).isSolid();
                            }
                            boolean nz = faceDefault;
                            if (worldZ > 0) {
                                nz = !world.getBlock(worldX, y, worldZ - 1).isSolid();
                            }
                            boolean pz = faceDefault;
                            if (worldZ < world.chunkLength * CHUNK_LENGTH - 1) {
                                pz = !world.getBlock(worldX, y, worldZ + 1).isSolid();
                            }

                            if(y == CHUNK_HEIGHT-1){
                                py = false;
                            }
                            //meshes[i].tint(this.applet.map(world.getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));
                            BlockGeometry.constructBlock(textureManager, world.getBlock(worldX, y, worldZ), meshes[i], nx, px, ny, py, nz, pz, x, y, z);
                        }
                    }
                }
            }
            meshes[i].endShape();
            subChunkDirtyList[i] = false; // All clean!
        }
    }


    private boolean isDirty() {
        for (int i = 0; i < subChunkDirtyList.length; i++) {
            if (subChunkDirtyList[i] == true) {
                return true;
            }
        }
        return false;
    }


    public void draw() {
        if (isDirty())
            regenerate();
        for (PShape mesh : meshes) {
            this.applet.shape(mesh);
        }
    }
}