package com.bobby;

import com.bobby.blocks.Block;
import com.bobby.blocks.BlockAir;
import com.bobby.blocks.BlockOak;
import com.bobby.blocks.BlockOakLeaves;
import com.bobby.blocks.construction.BlockGeometry;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;
import processing.opengl.PShader;

import java.util.ArrayList;

public class Chunk {

    private PApplet applet;

    private PShape meshes[];

    private PShape nonOpaqueMeshes[];

    public TextureManager textureManager;

    public PVector position;

    private World world;

    public final int CHUNK_WIDTH = 16, CHUNK_LENGTH = 16, CHUNK_HEIGHT = 256;
    private final int cwidth = 16, clength = 16, cheight = 16;
    private final int chunkStackHeight = CHUNK_HEIGHT / cheight; // 16 * 8 = 128;

    ArrayList<Block[][][]> subChunks;

    boolean[] subChunkDirtyList; // Flag to check if subchunk needs rebuilding
    boolean[] subNonOpaqueChunkDiryList;


    public Chunk(PApplet applet, World world, int x, int y) {
        this.world = world;
        this.position = new PVector(x, 0, y);


        this.applet = applet;

        textureManager = world.textureManager;

        subChunks = new ArrayList();
        meshes = new PShape[chunkStackHeight];
        nonOpaqueMeshes = new PShape[chunkStackHeight];
        subChunkDirtyList = new boolean[chunkStackHeight];

        //Initialize Everything
        for (int i = 0; i < chunkStackHeight; i++) {
            Block[][][] subChunk = new Block[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_LENGTH];
            subChunks.add(subChunk);
            meshes[i] = this.applet.createShape();
            nonOpaqueMeshes[i] = this.applet.createShape();
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

        if(x < 0 || y < 0 || z < 0 || x > cwidth - 1 || y > 256 - 1 || z > clength - 1){
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
                meshes[i] = this.applet.createShape();
                meshes[i].beginShape(this.applet.TRIANGLE);
                meshes[i].texture(textureManager.getTextureAtlas());
                meshes[i].noStroke();
                for (int x = 0; x < cwidth; x++) {
                    for (int y = 0 + (i * cheight); y < cheight + (i * cheight); y++) {
                        for (int z = 0; z < clength; z++) {

                            PVector worldSpace = toWorldSpace(x, y, z);
                            int worldX = (int) worldSpace.x;
                            int worldZ = (int) worldSpace.z;

                            if (world.getBlock(worldX, y, worldZ).isAir() || (!world.getBlock(worldX, y, worldZ).isOpaque() && world.getBlock(worldX, y, worldZ).isGlassLike())) {
                                continue;
                            }

                            boolean nx = faceDefault;
                            if (worldX > 0) {
                                nx = !world.getBlock(worldX - 1, y, worldZ).isSolid() || !world.getBlock(worldX - 1, y, worldZ).isOpaque();
                            }
                            boolean px = faceDefault;
                            if (worldX < world.chunkWidth * CHUNK_WIDTH - 1) {
                                px = !world.getBlock(worldX + 1, y, worldZ).isSolid() || !world.getBlock(worldX + 1, y, worldZ).isOpaque();
                            }
                            boolean ny = faceDefault;
                            if (y > 0) {
                                ny = !world.getBlock(worldX, y - 1, worldZ).isSolid() || !world.getBlock(worldX, y - 1, worldZ).isOpaque();
                            }
                            boolean py = faceDefault;
                            if (y < CHUNK_HEIGHT - 1) {
                                py = !world.getBlock(worldX, y + 1, worldZ).isSolid() || !world.getBlock(worldX, y + 1, worldZ).isOpaque();
                            }
                            boolean nz = faceDefault;
                            if (worldZ > 0) {
                                nz = !world.getBlock(worldX, y, worldZ - 1).isSolid() || !world.getBlock(worldX, y, worldZ - 1).isOpaque();
                            }
                            boolean pz = faceDefault;
                            if (worldZ < world.chunkLength * CHUNK_LENGTH - 1) {
                                pz = !world.getBlock(worldX, y, worldZ + 1).isSolid() || !world.getBlock(worldX, y, worldZ + 1).isOpaque();
                            }

                            if (y == CHUNK_HEIGHT - 1) {
                                py = false;
                            }

                            BlockGeometry.constructBlock(applet, textureManager, world.getBlock(worldX, y, worldZ), meshes[i], nx, px, ny, py, nz, pz, x, y, z);

                            //meshes[i].tint(this.applet.map(world.getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));

                        }
                    }
                }

                meshes[i].endShape();


//            for (int j = 0; j < meshes[i].getVertexCount(); j++) {
//                //meshes[i].setFill(j, 0xff0000);
//            }
            }
        }


        for (int i = 0; i < chunkStackHeight; i++) {
            if (subChunkDirtyList[i] == true) {
                System.out.println("SubChunk: " + i + " is dirty.");
                nonOpaqueMeshes[i] = this.applet.createShape();
                nonOpaqueMeshes[i].beginShape(this.applet.TRIANGLE);
                nonOpaqueMeshes[i].texture(this.textureManager.textureAtlas);
                nonOpaqueMeshes[i].noStroke();
                for (int x = 0; x < cwidth; x++) {
                    for (int y = 0 + (i * cheight); y < cheight + (i * cheight); y++) {
                        for (int z = 0; z < clength; z++) {

                            PVector worldSpace = toWorldSpace(x, y, z);
                            int worldX = (int) worldSpace.x;
                            int worldZ = (int) worldSpace.z;

                            if (world.getBlock(worldX, y, worldZ).isAir() || world.getBlock(worldX, y, worldZ).isOpaque() || !world.getBlock(worldX, y, worldZ).isGlassLike()) {
                                continue;
                            }

                            boolean nx = faceDefault;
                            if (worldX > 0) {
                                nx = world.getBlock(worldX - 1, y, worldZ).isOpaque() || world.getBlock(worldX - 1, y, worldZ).isAir() || !world.getBlock(worldX - 1, y, worldZ).isGlassLike();
                            }
                            boolean px = faceDefault;
                            if (worldX < world.chunkWidth * CHUNK_WIDTH - 1) {
                                px = world.getBlock(worldX + 1, y, worldZ).isOpaque() || world.getBlock(worldX + 1, y, worldZ).isAir() || !world.getBlock(worldX + 1, y, worldZ).isGlassLike();
                            }
                            boolean ny = faceDefault;
                            if (y > 0) {
                                ny = world.getBlock(worldX, y - 1, worldZ).isOpaque() || world.getBlock(worldX, y - 1, worldZ).isAir() || !world.getBlock(worldX, y - 1, worldZ).isGlassLike();
                            }
                            boolean py = faceDefault;
                            if (y < CHUNK_HEIGHT - 1) {
                                py = world.getBlock(worldX, y + 1, worldZ).isOpaque() || world.getBlock(worldX, y + 1, worldZ).isAir() || !world.getBlock(worldX, y + 1, worldZ).isGlassLike();
                            }
                            boolean nz = faceDefault;
                            if (worldZ > 0) {
                                nz = world.getBlock(worldX, y, worldZ - 1).isOpaque() || world.getBlock(worldX, y, worldZ - 1).isAir() || !world.getBlock(worldX, y, worldZ - 1).isGlassLike();
                            }
                            boolean pz = faceDefault;
                            if (worldZ < world.chunkLength * CHUNK_LENGTH - 1) {
                                pz = world.getBlock(worldX, y, worldZ + 1).isOpaque() || world.getBlock(worldX, y, worldZ + 1).isAir() || !world.getBlock(worldX, y, worldZ + 1).isGlassLike();
                            }

                            if (y == CHUNK_HEIGHT - 1) {
                                py = false;
                            }

                            BlockGeometry.constructBlock(applet, textureManager, world.getBlock(worldX, y, worldZ), nonOpaqueMeshes[i], nx, px, ny, py, nz, pz, x, y, z);

                            //meshes[i].tint(this.applet.map(world.getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));

                        }
                    }
                }
            }
            nonOpaqueMeshes[i].endShape();

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


    public void draw(PGraphics graphics, PShader normalShader, PShader auxShader) {
        if (isDirty())
            regenerate();
        for (PShape mesh : meshes) {
            graphics.shader(normalShader);
            graphics.shape(mesh);

        }
        for(PShape mesh : nonOpaqueMeshes){
            graphics.shader(normalShader);
            graphics.shape(mesh);
        }
    }
}