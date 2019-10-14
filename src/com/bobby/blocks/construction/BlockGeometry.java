package com.bobby.blocks.construction;

import com.bobby.TextureManager;
import com.bobby.blocks.Block;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class BlockGeometry {
    static PVector[] vertices = {
            new PVector(0, 0, 0),//0
            new PVector(1, 0, 0),//1
            new PVector(1, 0, 1),//2
            new PVector(0, 0, 1),//3
            new PVector(0, 1, 0),//4
            new PVector(1, 1, 0),//5
            new PVector(1, 1, 1),//6
            new PVector(0, 1, 1)};//7
    

    static int[] blockIndices = {
            //Back
            0, 1, 5,
            0, 5, 4,
            //Right
            1, 2, 6,
            1, 6, 5,
            //Front
            2, 3, 7,
            2, 7, 6,
            //Left
            3, 0, 4,
            3, 4, 7,
            //Top
            2, 1, 0,
            3, 2, 0,
            //Bottom
            4, 5, 6,
            4, 6, 7
    };

    static int[] spriteIndices = {
            0,2,6,
            0,6,4,

            3,1,5,
            3,5,7,

            2,0,4,
            2,4,6,

            1,3,7,
            1,7,5,


    };

    public static void constructBlock(PApplet applet, TextureManager manager, Block block, PShape shape, boolean negativeX, boolean positiveX, boolean negativeY, boolean positiveY, boolean negativeZ, boolean positiveZ, int x, int y, int z) {
        if(block.renderType == Block.BlockRenderType.BLOCK) {
            if (block.getName() != "Leaves") {
                int blockLight = (int) applet.map(applet.log(15 - block.lightLevel), 0, 3, 25, 255);
                blockLight = applet.max(blockLight, 25);
                blockLight = 255 - blockLight;
                shape.tint(applet.max(blockLight - 35, 0));
                if (negativeX)
                    //shape.fill(255, 0, 0, 255);
                    constructLeft(manager, block, shape, x, y, z);
                shape.tint(applet.max(blockLight - 35, 0));
                if (positiveX)
                    //shape.fill(255, 255, 0, 255);
                    constructRight(manager, block, shape, x, y, z);

                if (block.getName() == "Grass") {
                    shape.tint(blockLight * .4f, blockLight * .8f, blockLight * .2f);
                } else {
                    shape.tint(blockLight, 0);
                }
                if (negativeY)
                    //shape.fill(255, 0, 255, 255);
                    constructTop(manager, block, shape, x, y, z);
                shape.tint(applet.max(blockLight - 75, 0));
                if (positiveY)
                    //shape.fill(255, 255, 255, 255);
                    constructBottom(manager, block, shape, x, y, z);
                shape.tint(applet.max(blockLight - 35, 0));
                if (negativeZ)
                    constructBack(manager, block, shape, x, y, z);
                shape.tint(applet.max(blockLight - 35, 0));
                if (positiveZ)
                    constructFront(manager, block, shape, x, y, z);
            } else {
                int blockLight = (int) applet.map(applet.log(15 - block.lightLevel), 0, 3, 25, 255);
                blockLight = applet.max(blockLight, 25);
                blockLight = 255 - blockLight;
                shape.tint(0, applet.max(blockLight - 35, 0) * .8f, 0);
                if (negativeX)
                    //shape.fill(255, 0, 0, 255);
                    constructLeft(manager, block, shape, x, y, z);
                shape.tint(0, applet.max(blockLight - 35, 0) * .8f, 0);
                if (positiveX)
                    //shape.fill(255, 255, 0, 255);
                    constructRight(manager, block, shape, x, y, z);
                shape.tint(0, blockLight * .8f, 0);
                if (negativeY)
                    //shape.fill(255, 0, 255, 255);
                    constructTop(manager, block, shape, x, y, z);
                shape.tint(0, applet.max(blockLight - 75, 0) * .8f, 0);
                if (positiveY)
                    //shape.fill(255, 255, 255, 255);
                    constructBottom(manager, block, shape, x, y, z);
                shape.tint(0, applet.max(blockLight - 35, 0) * .8f, 0);
                if (negativeZ)
                    constructBack(manager, block, shape, x, y, z);
                shape.tint(0, applet.max(blockLight - 35, 0) * .8f, 0);
                if (positiveZ)
                    constructFront(manager, block, shape, x, y, z);
            }
        }else if(block.renderType == Block.BlockRenderType.SPRITE){
            int blockLight = (int) applet.map(applet.log(15 - block.lightLevel), 0, 3, 25, 255);
            blockLight = applet.max(blockLight, 25);
            blockLight = 255 - blockLight;

            if (block.getName() == "PlantGrass") {
                shape.tint(blockLight * .4f, blockLight * .8f, blockLight * .2f);
            } else {
                shape.tint(blockLight, 0);
            }
            shape.vertex(vertices[spriteIndices[0]].x + x, vertices[spriteIndices[0]].y + y, vertices[spriteIndices[0]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[1]].x + x, vertices[spriteIndices[1]].y + y, vertices[spriteIndices[1]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[2]].x + x, vertices[spriteIndices[2]].y + y, vertices[spriteIndices[2]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[3]].x + x, vertices[spriteIndices[3]].y + y, vertices[spriteIndices[3]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[4]].x + x, vertices[spriteIndices[4]].y + y, vertices[spriteIndices[4]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[5]].x + x, vertices[spriteIndices[5]].y + y, vertices[spriteIndices[5]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);

            shape.vertex(vertices[spriteIndices[6]].x + x, vertices[spriteIndices[6]].y + y, vertices[spriteIndices[6]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[7]].x + x, vertices[spriteIndices[7]].y + y, vertices[spriteIndices[7]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[8]].x + x, vertices[spriteIndices[8]].y + y, vertices[spriteIndices[8]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[9]].x + x, vertices[spriteIndices[9]].y + y, vertices[spriteIndices[9]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[10]].x + x, vertices[spriteIndices[10]].y + y, vertices[spriteIndices[10]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[11]].x + x, vertices[spriteIndices[11]].y + y, vertices[spriteIndices[11]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);

            shape.vertex(vertices[spriteIndices[12]].x + x, vertices[spriteIndices[12]].y + y, vertices[spriteIndices[12]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[13]].x + x, vertices[spriteIndices[13]].y + y, vertices[spriteIndices[13]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[14]].x + x, vertices[spriteIndices[14]].y + y, vertices[spriteIndices[14]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[15]].x + x, vertices[spriteIndices[15]].y + y, vertices[spriteIndices[15]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[16]].x + x, vertices[spriteIndices[16]].y + y, vertices[spriteIndices[16]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[17]].x + x, vertices[spriteIndices[17]].y + y, vertices[spriteIndices[17]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);

            shape.vertex(vertices[spriteIndices[18]].x + x, vertices[spriteIndices[18]].y + y, vertices[spriteIndices[18]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[19]].x + x, vertices[spriteIndices[19]].y + y, vertices[spriteIndices[19]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[20]].x + x, vertices[spriteIndices[20]].y + y, vertices[spriteIndices[20]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[21]].x + x, vertices[spriteIndices[21]].y + y, vertices[spriteIndices[21]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[spriteIndices[22]].x + x, vertices[spriteIndices[22]].y + y, vertices[spriteIndices[22]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[spriteIndices[23]].x + x, vertices[spriteIndices[23]].y + y, vertices[spriteIndices[23]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);

        }
    }

    private static void constructBack(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {
        shape.vertex(vertices[blockIndices[0]].x + x, vertices[blockIndices[0]].y + y, vertices[blockIndices[0]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[1]].x + x, vertices[blockIndices[1]].y + y, vertices[blockIndices[1]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[2]].x + x, vertices[blockIndices[2]].y + y, vertices[blockIndices[2]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[3]].x + x, vertices[blockIndices[3]].y + y, vertices[blockIndices[3]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[4]].x + x, vertices[blockIndices[4]].y + y, vertices[blockIndices[4]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[5]].x + x, vertices[blockIndices[5]].y + y, vertices[blockIndices[5]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);
    }

    private static void constructRight(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[blockIndices[6]].x + x, vertices[blockIndices[6]].y + y, vertices[blockIndices[6]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[7]].x + x, vertices[blockIndices[7]].y + y, vertices[blockIndices[7]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[8]].x + x, vertices[blockIndices[8]].y + y, vertices[blockIndices[8]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[9]].x + x, vertices[blockIndices[9]].y + y, vertices[blockIndices[9]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[10]].x + x, vertices[blockIndices[10]].y + y, vertices[blockIndices[10]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[11]].x + x, vertices[blockIndices[11]].y + y, vertices[blockIndices[11]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);
    }

    private static void constructFront(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[blockIndices[12]].x + x, vertices[blockIndices[12]].y + y, vertices[blockIndices[12]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[13]].x + x, vertices[blockIndices[13]].y + y, vertices[blockIndices[13]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[14]].x + x, vertices[blockIndices[14]].y + y, vertices[blockIndices[14]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[15]].x + x, vertices[blockIndices[15]].y + y, vertices[blockIndices[15]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[16]].x + x, vertices[blockIndices[16]].y + y, vertices[blockIndices[16]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[17]].x + x, vertices[blockIndices[17]].y + y, vertices[blockIndices[17]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);
    }

    private static void constructLeft(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[blockIndices[18]].x + x, vertices[blockIndices[18]].y + y, vertices[blockIndices[18]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[19]].x + x, vertices[blockIndices[19]].y + y, vertices[blockIndices[19]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[20]].x + x, vertices[blockIndices[20]].y + y, vertices[blockIndices[20]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[21]].x + x, vertices[blockIndices[21]].y + y, vertices[blockIndices[21]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).minY);
        shape.vertex(vertices[blockIndices[22]].x + x, vertices[blockIndices[22]].y + y, vertices[blockIndices[22]].z + z, manager.getTextureIndex(block.texture.SIDES).minX, manager.getTextureIndex(block.texture.SIDES).maxY);
        shape.vertex(vertices[blockIndices[23]].x + x, vertices[blockIndices[23]].y + y, vertices[blockIndices[23]].z + z, manager.getTextureIndex(block.texture.SIDES).maxX, manager.getTextureIndex(block.texture.SIDES).maxY);
    }

    private static void constructTop(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {
        shape.fill(0xFF0000);
        shape.vertex(vertices[blockIndices[24]].x + x, vertices[blockIndices[24]].y + y, vertices[blockIndices[24]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).minY);
        shape.vertex(vertices[blockIndices[25]].x + x, vertices[blockIndices[25]].y + y, vertices[blockIndices[25]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).maxY);
        shape.vertex(vertices[blockIndices[26]].x + x, vertices[blockIndices[26]].y + y, vertices[blockIndices[26]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).maxY);
        shape.vertex(vertices[blockIndices[27]].x + x, vertices[blockIndices[27]].y + y, vertices[blockIndices[27]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).minY);
        shape.vertex(vertices[blockIndices[28]].x + x, vertices[blockIndices[28]].y + y, vertices[blockIndices[28]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).minY);
        shape.vertex(vertices[blockIndices[29]].x + x, vertices[blockIndices[29]].y + y, vertices[blockIndices[29]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).maxY);
    }

    private static void constructBottom(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[blockIndices[30]].x + x, vertices[blockIndices[30]].y + y, vertices[blockIndices[30]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        shape.vertex(vertices[blockIndices[31]].x + x, vertices[blockIndices[31]].y + y, vertices[blockIndices[31]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        shape.vertex(vertices[blockIndices[32]].x + x, vertices[blockIndices[32]].y + y, vertices[blockIndices[32]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
        shape.vertex(vertices[blockIndices[33]].x + x, vertices[blockIndices[33]].y + y, vertices[blockIndices[33]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        shape.vertex(vertices[blockIndices[34]].x + x, vertices[blockIndices[34]].y + y, vertices[blockIndices[34]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
        shape.vertex(vertices[blockIndices[35]].x + x, vertices[blockIndices[35]].y + y, vertices[blockIndices[35]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
    }
}