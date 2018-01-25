package com.bobby.blocks;


import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class Block {

    private int lightLevel;
    public BlockSimpleMultiTexture texture;

    protected String name = "genericBlockName";

    public Block() {
        this.lightLevel = 15;
    }

    public void setLightLevel(int level) {
        if (level < 0)
            level = 0;
        if (level > 15)
            level = 15;
        this.lightLevel = level;
    }

    public int getLightLevel(){
        return this.lightLevel;
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
}