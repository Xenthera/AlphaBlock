package com.bobby;

import com.bobby.FastNoise.FastNoise;
import com.bobby.blocks.*;
import com.bobby.blocks.BlockBedrock;
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
    int x_blocks = chunkWidth * 16;
    int z_blocks = chunkLength * 16;
    int y_blocks = 256;
    FastNoise fastNoise;
    TextureManager textureManager;

    float progress = 0;
    String progressType = "";


    public World(PApplet applet){
        this.applet = applet;
        this.textureManager = new TextureManager(applet);
    }

    public PVector getRandomSpawnPoint(){
        int randX = (int)applet.random(16 * chunkWidth) - 1;
        int randZ = (int)applet.random(16 * chunkLength) - 1;
        int randY = 0;
        for (int i = 0; i < 256; i++) {
            if(this.getBlock(randX, i, randZ).isSolid()){
                randY = i;
                break;
            }
        }

        return new PVector(randX, randY, randZ);
    }

    public void createTree(int x, int y, int z){
        int height;
        float type = applet.random(1f);

        height = (int) applet.random(4, 8);

        if(applet.random(1) > 0.5) {

            for (int k = 0; k < height; k++) {
                if (k < height / 2) {
                    if (type >= 0.5f && k % 2 == 0) {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                if (applet.random(1) < 0.8f)
                                    this.setBlock(new BlockOakLeaves(), x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    } else {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                this.setBlock(new BlockOakLeaves(), x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    }
                }
                this.setBlock(new BlockOak(), x, y - height + k, z, false);
            }
            this.setBlock(new BlockOakLeaves(), x, y - height - 1, z, false);
        }else{
            for (int k = 0; k < height; k++) {
                if (k < height / 2) {
                    if (type >= 0.5f && k % 2 == 0) {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                if (applet.random(1) < 0.8f)
                                    this.setBlock(new BlockBirchLeaves(), x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    } else {
                        for (int treeWidth = (k == height / 2 - 1 ? -2 : -1); treeWidth <= (k == height / 2 - 1 ? 2 : 1); treeWidth++) {
                            for (int treeLength = (k == height / 2 - 1 ? -2 : -1); treeLength <= (k == height / 2 - 1 ? 2 : 1); treeLength++) {
                                this.setBlock(new BlockBirchLeaves(), x + treeWidth, y - height + k, z + treeLength, false);
                            }
                        }
                    }
                }
                this.setBlock(new BlockBirch(), x, y - height + k, z, false);
            }
            this.setBlock(new BlockBirchLeaves(), x, y - height - 1, z, false);
        }
    }

    public void load(){

        fastNoise = new FastNoise();
        fastNoise.SetSeed((int)applet.random(Integer.MAX_VALUE));
        fastNoise.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
        fastNoise.SetFractalOctaves(2);

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
        float frequency = 4.0f;

        for (int i = 0; i < this.x_blocks; i++) {
            for (int j = 0; j < this.y_blocks; j++) {
                for (int k = 0; k < this.z_blocks; k++) {

                    float rand = applet.pow(j / ((float)y_blocks) * 2, 1.1024f) + fastNoise.GetValueFractal(i * frequency, j * frequency, k * frequency) * 0.60f;
                    if (rand > 0.5f)
                    {
                        this.setBlock(new BlockStone(), i, j + 120, k, false);
                    }



                    if(j == 254){
                        if(applet.random(1) >= .5f){
                            this.setBlock(new BlockBedrock(), i, j, k, false);
                        }
                    }

                    if(j == 255){
                        this.setBlock(new BlockBedrock(), i, j, k, false);
                    }


                    chunkCount++;
                    this.progressType = "Generating terrain: " + (int)chunkCount + "/" + (x_blocks * y_blocks * z_blocks);
                    this.progress = chunkCount / (x_blocks * y_blocks * z_blocks);
                }
            }
        }
        chunkCount = 0;
        for (int x = 0; x < this.x_blocks; x++) {
            for (int z = 0; z < this.z_blocks; z++) {
                for (int y = 0; y < this.y_blocks; y++) {

                    if(this.getBlock(x,y,z).getName() != "Air"){
                        if(y / ((float)this.y_blocks) > 0.9f){
                            if(fastNoise.GetValueFractal(x * frequency * 5, z * frequency * 5) >= -0.2){
                                this.setBlock(new BlockSand(), x,y,z,false);
                                this.setBlock(new BlockSandstone(), x,y + 1,z,false);
                                this.setBlock(new BlockSandstone(), x,y + 2,z,false);
                                this.setBlock(new BlockSandstone(), x,y + 3,z,false);
                            }else{
                                this.setBlock(new BlockGravel(), x,y,z,false);
                                this.setBlock(new BlockGravel(), x,y + 1,z,false);
                                this.setBlock(new BlockGravel(), x,y + 2,z,false);
                                this.setBlock(new BlockGravel(), x,y + 3,z,false);
                            }
                        }else{
                            this.setBlock(new BlockGrass(), x,y,z, false);
                            this.setBlock(new BlockDirt(), x,y + 1,z,false);
                            this.setBlock(new BlockDirt(), x,y + 2,z,false);
                            this.setBlock(new BlockDirt(), x,y + 3,z,false);
                        }
                        break;
                    }


                    chunkCount++;
                    this.progressType = "Decorating Terrain: " + (int)chunkCount + "/" + (x_blocks * y_blocks * z_blocks);
                    this.progress = chunkCount / (x_blocks * y_blocks * z_blocks);
                }
            }
        }
        for (int x = 0; x < this.x_blocks; x++) {
            for (int z = 0; z < this.z_blocks; z++) {
                for (int y = 0; y < this.y_blocks; y++) {

                    if(this.getBlock(x,y,z).getName() != "Air"){
                        if(applet.random(1) > 0.945f && (getBlock(x,y,z).getName().equals("Grass"))){
                            this.createTree(x ,y ,z);
                        }
                        break;
                    }
                }
            }
        }
        chunkCount = 0;
        for (int x = 0; x < this.x_blocks; x++) {
            for (int z = 0; z < this.z_blocks; z++) {

                int natural = 15;

                for (int y = 0; y < this.y_blocks; y++) {

                    this.getBlock(x,y,z).setLightLevel(natural);

                    if(this.getBlock(x,y,z).isOpaque() || this.getBlock(x,y,z).getName() == "Leaves"){
                        natural = 0;
                    }

                    chunkCount++;
                    this.progressType = "Generating Lighting: " + (int)chunkCount + " of " + (x_blocks * y_blocks * z_blocks);
                    this.progress = chunkCount / (x_blocks * y_blocks * z_blocks);
                }
            }
        }
        ArrayList<PVector> light_queue = new ArrayList<>();
        chunkCount = 0;
        for (int x = 0; x < this.x_blocks; x++) {
            for (int z = 0; z < this.z_blocks; z++) {
                for (int y = 0; y < this.y_blocks; y++) {

                    if(this.getBlock(x,y,z).getLightLevel() == 0){

                        if(     this.getBlock(x + 1, y, z).getLightLevel() == 15 ||
                                this.getBlock(x - 1, y, z).getLightLevel() == 15 ||

                                this.getBlock(x, y + 1, z).getLightLevel() == 15 ||
                                this.getBlock(x, y - 1, z).getLightLevel() == 15 ||

                                this.getBlock(x, y, z + 1).getLightLevel() == 15 ||
                                this.getBlock(x, y, z - 1).getLightLevel() == 15)
                        {
                            this.getBlock(x,y,z).setLightLevel(14);
                            light_queue.add(new PVector(x,y,z));
                        }
                    }
                    chunkCount++;
                    this.progressType = "Propagating Light: " + (int)chunkCount + "/" + (x_blocks * y_blocks * z_blocks);
                    this.progress = chunkCount / (x_blocks * y_blocks * z_blocks);
                }
            }
        }

        while(light_queue.size() > 0){
            int x = (int)light_queue.get(light_queue.size() - 1).x;
            int y = (int)light_queue.get(light_queue.size() - 1).y;
            int z = (int)light_queue.get(light_queue.size() - 1).z;

            light_queue.remove(light_queue.size() - 1);

            int current_value = this.getBlock(x,y,z).getLightLevel();

            if(this.inBounds(x + 1,y,z) && this.getBlock(x + 1,y,z).getLightLevel() < current_value - 1){
                this.getBlock(x + 1, y, z).setLightLevel(current_value-1);
                light_queue.add(new PVector(x + 1, y, z));
            }
            if(this.inBounds(x - 1,y,z) && this.getBlock(x - 1,y,z).getLightLevel() < current_value - 1){
                this.getBlock(x - 1, y, z).setLightLevel(current_value-1);
                light_queue.add(new PVector(x - 1, y, z));
            }

            if(this.inBounds(x, y + 1, z) && this.getBlock(x, y + 1, z).getLightLevel() < current_value - 1){
                this.getBlock(x, y + 1, z).setLightLevel(current_value-1);
                light_queue.add(new PVector(x, y + 1, z));
            }
            if(this.inBounds(x, y - 1, z) && this.getBlock(x, y - 1, z).getLightLevel() < current_value - 1){
                this.getBlock(x, y - 1, z).setLightLevel(current_value-1);
                light_queue.add(new PVector(x, y - 1, z));
            }

            if(this.inBounds(x, y, z + 1) && this.getBlock(x, y, z + 1).getLightLevel() < current_value - 1){
                this.getBlock(x, y, z + 1).setLightLevel(current_value-1);
                light_queue.add(new PVector(x, y, z + 1));
            }
            if(this.inBounds(x, y, z - 1) && this.getBlock(x,y,z-1).getLightLevel() < current_value - 1){
                this.getBlock(x, y, z - 1).setLightLevel(current_value-1);
                light_queue.add(new PVector(x, y, z - 1));
            }
        }
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

    public boolean inBounds(int x, int y, int z){
        return !(x < 0 || y < 0 || z < 0 || x > chunkWidth * 16 - 1 || y > 256 - 1 || z > chunkLength * 16 - 1);
    }

    public Block getBlock(int x, int y, int z) {
        if(!this.inBounds(x,y,z)){
            return new BlockAir();
        }

        int chunkX = x / 16;
        int blockX = x % 16;
        int chunkZ = z / 16;
        int blockZ = z % 16;

        return chunks.get(chunkX * chunkWidth + chunkZ).getBlock(blockX, y, blockZ);

    }



    public void setBlock(Block block, int x, int y, int z, boolean setByUser) {
        if(!this.inBounds(x,y,z)){
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
