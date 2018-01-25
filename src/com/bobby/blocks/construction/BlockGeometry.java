package com.bobby.blocks.construction;

import com.bobby.TextureManager;
import com.bobby.blocks.Block;
import processing.core.PShape;
import processing.core.PVector;

public class BlockGeometry {
    static PVector[] vertices = {new PVector(0, 0, 0),
            new PVector(1, 0, 0),
            new PVector(1, 0, 1),
            new PVector(0, 0, 1),
            new PVector(0, 1, 0),
            new PVector(1, 1, 0),
            new PVector(1, 1, 1),
            new PVector(0, 1, 1)};
    

    static int[] indices = {
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
            4, 6, 7};

    public static void constructBlock(TextureManager manager, Block block, PShape shape, boolean negativeX, boolean positiveX, boolean negativeY, boolean positiveY, boolean negativeZ, boolean positiveZ, int x, int y, int z) {
        if (negativeX)
            constructLeft(manager, block, shape, x, y, z);
        if (positiveX)
            constructRight(manager, block, shape, x, y, z);
        if (negativeY)
            constructTop(manager, block, shape, x, y, z);
        if (positiveY)
            constructBottom(manager, block, shape, x, y, z);
        if (negativeZ)
            constructBack(manager, block, shape, x, y, z);
        if (positiveZ)
            constructFront(manager, block, shape, x, y, z);
    }

    private static void constructBack(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {
        shape.vertex(vertices[indices[0]].x + x, vertices[indices[0]].y + y, vertices[indices[0]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[1]].x + x, vertices[indices[1]].y + y, vertices[indices[1]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[2]].x + x, vertices[indices[2]].y + y, vertices[indices[2]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[3]].x + x, vertices[indices[3]].y + y, vertices[indices[3]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[4]].x + x, vertices[indices[4]].y + y, vertices[indices[4]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[5]].x + x, vertices[indices[5]].y + y, vertices[indices[5]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }

    private static void constructRight(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[6]].x + x, vertices[indices[6]].y + y, vertices[indices[6]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[7]].x + x, vertices[indices[7]].y + y, vertices[indices[7]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[8]].x + x, vertices[indices[8]].y + y, vertices[indices[8]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[9]].x + x, vertices[indices[9]].y + y, vertices[indices[9]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[10]].x + x, vertices[indices[10]].y + y, vertices[indices[10]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[11]].x + x, vertices[indices[11]].y + y, vertices[indices[11]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }

    private static void constructFront(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[12]].x + x, vertices[indices[12]].y + y, vertices[indices[12]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[13]].x + x, vertices[indices[13]].y + y, vertices[indices[13]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[14]].x + x, vertices[indices[14]].y + y, vertices[indices[14]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[15]].x + x, vertices[indices[15]].y + y, vertices[indices[15]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[16]].x + x, vertices[indices[16]].y + y, vertices[indices[16]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[17]].x + x, vertices[indices[17]].y + y, vertices[indices[17]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }

    private static void constructLeft(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[18]].x + x, vertices[indices[18]].y + y, vertices[indices[18]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[19]].x + x, vertices[indices[19]].y + y, vertices[indices[19]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[20]].x + x, vertices[indices[20]].y + y, vertices[indices[20]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[21]].x + x, vertices[indices[21]].y + y, vertices[indices[21]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[22]].x + x, vertices[indices[22]].y + y, vertices[indices[22]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[23]].x + x, vertices[indices[23]].y + y, vertices[indices[23]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }

    private static void constructTop(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[24]].x + x, vertices[indices[24]].y + y, vertices[indices[24]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[25]].x + x, vertices[indices[25]].y + y, vertices[indices[25]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[26]].x + x, vertices[indices[26]].y + y, vertices[indices[26]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[27]].x + x, vertices[indices[27]].y + y, vertices[indices[27]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[28]].x + x, vertices[indices[28]].y + y, vertices[indices[28]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[29]].x + x, vertices[indices[29]].y + y, vertices[indices[29]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }

    private static void constructBottom(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[30]].x + x, vertices[indices[30]].y + y, vertices[indices[30]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[31]].x + x, vertices[indices[31]].y + y, vertices[indices[31]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[32]].x + x, vertices[indices[32]].y + y, vertices[indices[32]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[33]].x + x, vertices[indices[33]].y + y, vertices[indices[33]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).minY);
        shape.vertex(vertices[indices[34]].x + x, vertices[indices[34]].y + y, vertices[indices[34]].z + z, manager.getTextureIndex(block.texture).minX, manager.getTextureIndex(block.texture).maxY);
        shape.vertex(vertices[indices[35]].x + x, vertices[indices[35]].y + y, vertices[indices[35]].z + z, manager.getTextureIndex(block.texture).maxX, manager.getTextureIndex(block.texture).maxY);
    }
}