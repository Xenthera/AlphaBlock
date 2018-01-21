package com.bobby;
import processing.core.PVector;
import processing.core.PShape;

public class BlockGeometry {
    static PVector[] vertices = {new PVector(-0.5f, -0.5f, -0.5f),
            new PVector(0.5f, -0.5f, -0.5f),
            new PVector(0.5f, -0.5f, 0.5f),
            new PVector(-0.5f, -0.5f, 0.5f),
            new PVector(-0.5f, 0.5f, -0.5f),
            new PVector(0.5f, 0.5f, -0.5f),
            new PVector(0.5f, 0.5f, 0.5f),
            new PVector(-0.5f, 0.5f, 0.5f)};
    

    static int[] indices = {
            //Back
            1, 2, 6,
            1, 6, 5,
            //Right
            2, 3, 7,
            2, 7, 6,
            //Front
            3, 4, 8,
            3, 8, 7,
            //Left
            4, 1, 5,
            4, 5, 8,
            //Top
            3, 2, 1,
            4, 3, 1,
            //Bottom
            5, 6, 7,
            5, 7, 8};

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
        shape.vertex(vertices[indices[0]-1].x + x, vertices[indices[0]-1].y + y, vertices[indices[0]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[1]-1].x + x, vertices[indices[1]-1].y + y, vertices[indices[1]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[2]-1].x + x, vertices[indices[2]-1].y + y, vertices[indices[2]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[3]-1].x + x, vertices[indices[3]-1].y + y, vertices[indices[3]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[4]-1].x + x, vertices[indices[4]-1].y + y, vertices[indices[4]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[5]-1].x + x, vertices[indices[5]-1].y + y, vertices[indices[5]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }

    private static void constructRight(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[6]-1].x + x, vertices[indices[6]-1].y + y, vertices[indices[6]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[7]-1].x + x, vertices[indices[7]-1].y + y, vertices[indices[7]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[8]-1].x + x, vertices[indices[8]-1].y + y, vertices[indices[8]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[9]-1].x + x, vertices[indices[9]-1].y + y, vertices[indices[9]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[10]-1].x + x, vertices[indices[10]-1].y + y, vertices[indices[10]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[11]-1].x + x, vertices[indices[11]-1].y + y, vertices[indices[11]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }

    private static void constructFront(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[12]-1].x + x, vertices[indices[12]-1].y + y, vertices[indices[12]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[13]-1].x + x, vertices[indices[13]-1].y + y, vertices[indices[13]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[14]-1].x + x, vertices[indices[14]-1].y + y, vertices[indices[14]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[15]-1].x + x, vertices[indices[15]-1].y + y, vertices[indices[15]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[16]-1].x + x, vertices[indices[16]-1].y + y, vertices[indices[16]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[17]-1].x + x, vertices[indices[17]-1].y + y, vertices[indices[17]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }

    private static void constructLeft(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[18]-1].x + x, vertices[indices[18]-1].y + y, vertices[indices[18]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[19]-1].x + x, vertices[indices[19]-1].y + y, vertices[indices[19]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[20]-1].x + x, vertices[indices[20]-1].y + y, vertices[indices[20]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[21]-1].x + x, vertices[indices[21]-1].y + y, vertices[indices[21]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[22]-1].x + x, vertices[indices[22]-1].y + y, vertices[indices[22]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[23]-1].x + x, vertices[indices[23]-1].y + y, vertices[indices[23]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }

    private static void constructTop(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[24]-1].x + x, vertices[indices[24]-1].y + y, vertices[indices[24]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[25]-1].x + x, vertices[indices[25]-1].y + y, vertices[indices[25]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[26]-1].x + x, vertices[indices[26]-1].y + y, vertices[indices[26]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[27]-1].x + x, vertices[indices[27]-1].y + y, vertices[indices[27]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[28]-1].x + x, vertices[indices[28]-1].y + y, vertices[indices[28]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[29]-1].x + x, vertices[indices[29]-1].y + y, vertices[indices[29]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }

    private static void constructBottom(TextureManager manager, Block block, PShape shape, int  x, int y, int z) {

        shape.vertex(vertices[indices[30]-1].x + x, vertices[indices[30]-1].y + y, vertices[indices[30]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[31]-1].x + x, vertices[indices[31]-1].y + y, vertices[indices[31]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[32]-1].x + x, vertices[indices[32]-1].y + y, vertices[indices[32]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[33]-1].x + x, vertices[indices[33]-1].y + y, vertices[indices[33]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 0.15f + manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[34]-1].x + x, vertices[indices[34]-1].y + y, vertices[indices[34]-1].z + z, 0.15f + manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
        shape.vertex(vertices[indices[35]-1].x + x, vertices[indices[35]-1].y + y, vertices[indices[35]-1].z + z, 15.85f +  manager.getTextureIndex(block.texture).x, 15.85f +  manager.getTextureIndex(block.texture).y);
    }
}