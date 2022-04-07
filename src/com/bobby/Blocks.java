package com.bobby;

import com.bobby.blocks.*;
import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class Blocks {

    //region Block Classes
    private static final BlockAir BLOCK_AIR = new BlockAir();
    private static final BlockBedrock BLOCK_BEDROCK = new BlockBedrock();
    private static final BlockBirch BLOCK_BIRCH = new BlockBirch();
    private static final BlockBirchLeaves BLOCK_BIRCH_LEAVES = new BlockBirchLeaves();
    private static final BlockDeadShrub BLOCK_DEAD_SHRUB = new BlockDeadShrub();
    private static final BlockDirt BLOCK_DIRT  = new BlockDirt();
    private static final BlockFlower BLOCK_FLOWER  = new BlockFlower();
    private static final BlockGrass BLOCK_GRASS = new BlockGrass();
    private static final BlockGravel BLOCK_GRAVEL = new BlockGravel();
    private static final BlockOak BLOCK_OAK = new BlockOak();
    private static final BlockOakLeaves BLOCK_OAK_LEAVES = new BlockOakLeaves();
    private static final BlockPlantGrass BLOCK_PLANT_GRASS = new BlockPlantGrass();
    private static final BlockReed BLOCK_REED = new BlockReed();
    private static final BlockSand BLOCK_SAND = new BlockSand();
    private static final BlockSandstone BLOCK_SANDSTONE = new BlockSandstone();
    private static final BlockStone BLOCK_STONE = new BlockStone();
    private static final BlockStoneBrick BLOCK_STONE_BRICK = new BlockStoneBrick();
    private static final BlockOakPlanks BLOCK_OAK_PLANKS = new BlockOakPlanks();
    private static final BlockGlass BLOCK_GLASS = new BlockGlass();
    private static final BlockWater BLOCK_WATER = new BlockWater();
    //endregion

    public static final short AIR = 0;
    public static final short BEDROCK = 1;
    public static final short BIRCH = 2;
    public static final short BIRCH_LEAVES = 3;
    public static final short DEAD_SHRUB = 4;
    public static final short DIRT  = 5;
    public static final short FLOWER  = 6;
    public static final short GRASS = 7;
    public static final short GRAVEL = 8;
    public static final short OAK = 9;
    public static final short OAK_LEAVES = 10;
    public static final short PLANT_GRASS = 11;
    public static final short REED = 12;
    public static final short SAND = 13;
    public static final short SANDSTONE = 14;
    public static final short STONE = 15;
    public static final short STONE_BRICK = 16;
    public static final short OAK_PLANKS = 17;
    public static final short GLASS = 18;
    public static final short WATER = 19;

    public static final Block[] BLOCKS = new Block[]{BLOCK_AIR, BLOCK_BEDROCK, BLOCK_BIRCH, BLOCK_BIRCH_LEAVES, BLOCK_DEAD_SHRUB, BLOCK_DIRT, BLOCK_FLOWER, BLOCK_GRASS, BLOCK_GRAVEL, BLOCK_OAK, BLOCK_OAK_LEAVES, BLOCK_PLANT_GRASS, BLOCK_REED, BLOCK_SAND, BLOCK_SANDSTONE, BLOCK_STONE, BLOCK_STONE_BRICK, BLOCK_OAK_PLANKS, BLOCK_GLASS, BLOCK_WATER};

    public static boolean IsSolid(int blockID){
        return BLOCKS[blockID].isSolid();
    }
    public static boolean IsAir(int blockID){
        return BLOCKS[blockID].isAir();
    }
    public static boolean IsOpaque(int blockID){
        return BLOCKS[blockID].isOpaque();
    }
    public static boolean IsGlassLike(int blockID){
        return BLOCKS[blockID].isGlassLike();
    }

    public static Block.BlockRenderType RenderType(int blockID){
        return BLOCKS[blockID].renderType;
    }

    public static BlockSimpleMultiTexture Texture(int blockID){
        return BLOCKS[blockID].texture;
    }

}
