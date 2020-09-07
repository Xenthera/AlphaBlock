package com.bobby.blocks;


import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class Block {

    public enum BlockRenderType {
        BLOCK,
        SPRITE
    }

    public int lightLevel;

    public BlockSimpleMultiTexture texture;

    public BlockRenderType renderType;

    protected String name = "genericBlockName";

    public Block() {
        this.lightLevel = 15;
        this.renderType = BlockRenderType.BLOCK;
        name = this.toString();
    }

    public boolean isSolid() {
        return true;
    }
    public boolean isOpaque(){
        return true;
    }

    public String getName(){
        return this.name;
    }

    public Block getBlock(int x){
        float y = 1;
        return new BlockFlower();
    }

    public boolean isAir(){
        return false;
    }

    public boolean isGlassLike(){
        return false;
    }
}