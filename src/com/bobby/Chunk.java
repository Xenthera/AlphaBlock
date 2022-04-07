package com.bobby;

import com.bobby.FastNoise.FastNoise;
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

    //FastNoise fastNoise;

    private PShape meshes[];

    private PShape nonOpaqueMeshes[];

    public TextureManager textureManager;

    public Tuple position;

    public World world;

    public final int CHUNK_WIDTH = 16, CHUNK_LENGTH = 16, CHUNK_HEIGHT = 256;
    private final int cwidth = 16, clength = 16, cheight = 16;
    private final int chunkStackHeight = CHUNK_HEIGHT / cheight; // 16 * 8 = 128;

    ArrayList<byte[][][]> subChunks;
    ArrayList<short[][][]> subChunksLightLevels;

    boolean[] subChunkDirtyList; // Flag to check if subchunk needs rebuilding



    public Chunk(PApplet applet, World world, int x, int y) {

        this.world = world;

        this.position = new Tuple(x, y);
        //fastNoise = new FastNoise();
        //fastNoise.SetSeed((int)applet.random(Integer.MAX_VALUE));
        //fastNoise.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
        //fastNoise.SetFractalOctaves(2);

        this.applet = applet;

        textureManager = world.textureManager;

        subChunks = new ArrayList();
        subChunksLightLevels = new ArrayList();
        meshes = new PShape[chunkStackHeight];
        nonOpaqueMeshes = new PShape[chunkStackHeight];
        subChunkDirtyList = new boolean[chunkStackHeight];

        //Initialize Everything
        for (int i = 0; i < chunkStackHeight; i++) {
            byte[][][] subChunk = new byte[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_LENGTH];
            //short[][][] subChunkLightLevels = new short[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_LENGTH];
            subChunks.add(subChunk);
            //subChunksLightLevels.add(subChunkLightLevels);
            //meshes[i] = this.applet.createShape();
            //nonOpaqueMeshes[i] = this.applet.createShape();
            subChunkDirtyList[i] = true;
        }

        float frequency = 4.0f;

        for (int i = 0; i < CHUNK_WIDTH; i++) {
            for (int j = 0; j < CHUNK_HEIGHT; j++) {
                for (int k = 0; k < CHUNK_LENGTH; k++) {

                    int worldX = i + (CHUNK_WIDTH * x);
                    int worldZ = j + (CHUNK_LENGTH * y);
                    double ypos = Math.sin(worldX * 0.25) * 15;
                    int yy = CHUNK_HEIGHT - (int)ypos - 61;
                    if(j >= yy)
                    this.setBlock(Blocks.OAK, i, j, k, false);

//                    float rand = applet.pow(j / ((float)CHUNK_HEIGHT) * 4, 1.1024f) + fastNoise.GetValueFractal((i + (CHUNK_WIDTH * position.x)) * frequency, j * frequency, (k + (CHUNK_LENGTH * position.y)) * frequency) * 0.60f;
//                    if (rand > 0.5f)
//                    {
//                        this.setBlock(Blocks.BIRCH_LEAVES, i, j + 120, k, false);
//                    }
//
//
//
//                    if(j == 254){
//                        if(applet.random(1) >= .5f){
//                            this.setBlock(Blocks.BEDROCK, i, j, k, false);
//                        }
//                    }
//
//                    if(j == 255){
//                        this.setBlock(Blocks.BEDROCK, i, j, k, false);
//                    }
//
//                    if(this.getBlock(i,j,k) != Blocks.AIR){
//                        if(y / ((float)this.CHUNK_HEIGHT) > 0.65f){
//                            if(fastNoise.GetValueFractal(i * frequency * 5, k * frequency * 5) >= -0.2){
//                                this.setBlock(Blocks.SAND, i,j,k,false);
//                                this.setBlock(Blocks.SANDSTONE, i,j + 1,k,false);
//                                this.setBlock(Blocks.SANDSTONE, i,j + 2,k,false);
//                                this.setBlock(Blocks.SANDSTONE, i,j + 3,k,false);
//                            }else{
//                                this.setBlock(Blocks.GRAVEL, i,j,k,false);
//                                this.setBlock(Blocks.GRAVEL, i,j + 1,k,false);
//                                this.setBlock(Blocks.GRAVEL, i,j + 2,k,false);
//                                this.setBlock(Blocks.GRAVEL, i,j + 3,k,false);
//                            }
//                        }else{
//                            this.setBlock(Blocks.GRASS, i,j,k, false);
//                            this.setBlock(Blocks.DIRT, i,j + 1,k,false);
//                            this.setBlock(Blocks.DIRT, i,j + 2,k,false);
//                            this.setBlock(Blocks.DIRT, i,j + 3,k,false);
//                            if(applet.random(1) < 0.4) {
//                                this.setBlock(Blocks.PLANT_GRASS, i, j - 1, k, false);
//                            }
//                        }
//                        break;
//                    }


                }

            }
        }

    }



    public int getBlock(int x, int y, int z) {
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        return subChunks.get(chunkLocation)[x][blockLocation][z];


    }

    public void setBlock(short block, int x, int y, int z, boolean setByUser) {
        int chunkLocation = y / cwidth;
        int blockLocation = y % cwidth;

        if(x < 0 || y < 0 || z < 0 || x > cwidth - 1 || y > 256 - 1 || z > clength - 1){
            return;
        }

        byte[][][] blocks = subChunks.get(chunkLocation);

        blocks[x][blockLocation][z] = (byte)block;

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

        // If the block was placed by a user, update the neighboring chunks if on the edge of a chunk.

        if (x == 0) {
            Chunk nx = world.getChunk(this.position.x - 1, this.position.y);
            if(nx != null) {
                if (Blocks.IsSolid(nx.getBlock(cwidth - 1, y, z))) {

                    nx.subChunkDirtyList[chunkLocation] = true;
                    //nx.regenerate();
                }
            }
        }
        if (x == cwidth - 1) {
            Chunk nx = world.getChunk(this.position.x + 1, this.position.y);
            if(nx != null) {
                if (Blocks.IsSolid(nx.getBlock(0, y, z))) {
                    nx.subChunkDirtyList[chunkLocation] = true;
                    //nx.regenerate();
                }
            }
        }
        if (z == 0) {
            Chunk nz = world.getChunk(this.position.x, this.position.y - 1);
            if(nz != null) {
                if (Blocks.IsSolid(nz.getBlock(x, y, clength - 1))) {
                    nz.subChunkDirtyList[chunkLocation] = true;
                    //nz.regenerate();
                }
            }
        }
        if (z == clength - 1) {
            Chunk nz = world.getChunk(this.position.x, this.position.y + 1);
            if(nz != null) {
                if (Blocks.IsSolid(nz.getBlock(x, y, 0))) {
                    nz.subChunkDirtyList[chunkLocation] = true;
                    //nz.regenerate();
                }
            }
        }

    }

    public PVector toWorldSpace(int x, int y, int z){
        return new PVector((int)(x + (this.position.x * cwidth)), y, (int)(z + (this.position.y * cwidth)));
    }

    public void removeBlock(int x, int y, int z, boolean setByUser) {
        this.setBlock(Blocks.AIR, x, y, z, setByUser);
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



                            if (Blocks.IsAir(getBlock(x, y, z)) || (!Blocks.IsOpaque(getBlock(x, y, z)) && Blocks.IsGlassLike(getBlock(x, y, z)))) {
                                continue;
                            }

                            boolean nx = faceDefault;
                            if (x > 0) {
                                nx = !Blocks.IsSolid(getBlock(x - 1, y, z)) || !Blocks.IsOpaque(getBlock(x - 1, y, z));
                            }
                            boolean px = faceDefault;
                            if (x < CHUNK_WIDTH - 1) {
                                px = !Blocks.IsSolid(getBlock(x + 1, y, z)) || !Blocks.IsOpaque(getBlock(x + 1, y, z));
                            }
                            boolean ny = faceDefault;
                            if (y > 0) {
                                ny = !Blocks.IsSolid(getBlock(x, y - 1, z)) || !Blocks.IsOpaque(getBlock(x, y - 1, z));
                            }
                            boolean py = faceDefault;
                            if (y < CHUNK_HEIGHT - 1) {
                                py = !Blocks.IsSolid(getBlock(x, y + 1, z)) || !Blocks.IsOpaque(getBlock(x, y + 1, z));
                            }
                            boolean nz = faceDefault;
                            if (z > 0) {
                                nz = !Blocks.IsSolid(getBlock(x, y, z - 1)) || !Blocks.IsOpaque(getBlock(x, y, z - 1));
                            }
                            boolean pz = faceDefault;
                            if (z < CHUNK_LENGTH - 1) {
                                pz = !Blocks.IsSolid(getBlock(x, y, z + 1)) || !Blocks.IsOpaque(getBlock(x, y, z + 1));
                            }

                            if (y == CHUNK_HEIGHT - 1) {
                                py = false;
                            }



                            BlockGeometry.constructBlock(applet, textureManager, getBlock(x, y, z), meshes[i], nx, px, ny, py, nz, pz, x, y, z);

                            //meshes[i].tint(this.applet.map(world.getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));

                        }
                    }
                }

                meshes[i].endShape();
                subChunkDirtyList[i] = false; // All clean!

//            for (int j = 0; j < meshes[i].getVertexCount(); j++) {
//                //meshes[i].setFill(j, 0xff0000);
//            }
            }
        }


//        for (int i = 0; i < chunkStackHeight; i++) {
//            if (subChunkDirtyList[i] == true) {
//                System.out.println("SubChunk: " + i + " is dirty.");
//                nonOpaqueMeshes[i] = this.applet.createShape();
//                nonOpaqueMeshes[i].beginShape(this.applet.TRIANGLE);
//                nonOpaqueMeshes[i].texture(this.textureManager.textureAtlas);
//                nonOpaqueMeshes[i].noStroke();
//                for (int x = 0; x < cwidth; x++) {
//                    for (int y = 0 + (i * cheight); y < cheight + (i * cheight); y++) {
//                        for (int z = 0; z < clength; z++) {
//
//                            PVector worldSpace = toWorldSpace(x, y, z);
//                            int worldX = (int) worldSpace.x;
//                            int worldZ = (int) worldSpace.z;
//
//                            if (world.getBlock(worldX, y, worldZ).isAir() || world.getBlock(worldX, y, worldZ).isOpaque() || !world.getBlock(worldX, y, worldZ).isGlassLike()) {
//                                continue;
//                            }
//
//                            boolean nx = faceDefault;
//                            if (worldX > 0) {
//                                nx = world.getBlock(worldX - 1, y, worldZ).isOpaque() || world.getBlock(worldX - 1, y, worldZ).isAir() || !world.getBlock(worldX - 1, y, worldZ).isGlassLike();
//                            }
//                            boolean px = faceDefault;
//                            if (worldX < world.chunkWidth * CHUNK_WIDTH - 1) {
//                                px = world.getBlock(worldX + 1, y, worldZ).isOpaque() || world.getBlock(worldX + 1, y, worldZ).isAir() || !world.getBlock(worldX + 1, y, worldZ).isGlassLike();
//                            }
//                            boolean ny = faceDefault;
//                            if (y > 0) {
//                                ny = world.getBlock(worldX, y - 1, worldZ).isOpaque() || world.getBlock(worldX, y - 1, worldZ).isAir() || !world.getBlock(worldX, y - 1, worldZ).isGlassLike();
//                            }
//                            boolean py = faceDefault;
//                            if (y < CHUNK_HEIGHT - 1) {
//                                py = world.getBlock(worldX, y + 1, worldZ).isOpaque() || world.getBlock(worldX, y + 1, worldZ).isAir() || !world.getBlock(worldX, y + 1, worldZ).isGlassLike();
//                            }
//                            boolean nz = faceDefault;
//                            if (worldZ > 0) {
//                                nz = world.getBlock(worldX, y, worldZ - 1).isOpaque() || world.getBlock(worldX, y, worldZ - 1).isAir() || !world.getBlock(worldX, y, worldZ - 1).isGlassLike();
//                            }
//                            boolean pz = faceDefault;
//                            if (worldZ < world.chunkLength * CHUNK_LENGTH - 1) {
//                                pz = world.getBlock(worldX, y, worldZ + 1).isOpaque() || world.getBlock(worldX, y, worldZ + 1).isAir() || !world.getBlock(worldX, y, worldZ + 1).isGlassLike();
//                            }
//
//                            if (y == CHUNK_HEIGHT - 1) {
//                                py = false;
//                            }
//
//                            BlockGeometry.constructBlock(applet, textureManager, world.getBlock(worldX, y, worldZ), nonOpaqueMeshes[i], nx, px, ny, py, nz, pz, x, y, z);
//
//                            //meshes[i].tint(this.applet.map(world.getBlock(x, y, z).getLightLevel(), 0, 15, 50, 255));
//
//                        }
//                    }
//                }
//            }
//            nonOpaqueMeshes[i].endShape();
//

//        }
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

        if (isDirty()) {
            regenerate();
        }
        for (PShape mesh : meshes) {
            graphics.shader(normalShader);
            graphics.shape(mesh);

        }
//        for(PShape mesh : nonOpaqueMeshes){
//            graphics.shader(normalShader);
//            graphics.shape(mesh);
//        }
    }
}