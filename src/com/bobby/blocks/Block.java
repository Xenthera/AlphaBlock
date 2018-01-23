package com.bobby.blocks;


public class Block {

    private int lightLevel;
    BlockSingleTexture texture;

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
}