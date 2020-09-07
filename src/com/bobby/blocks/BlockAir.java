package com.bobby.blocks;

public class BlockAir extends Block {

    public BlockAir() {
        super();
        this.name = "Air";
    }

    public boolean isSolid() {
        return false;
    }
    public boolean isOpaque() { return false; }

    @Override
    public boolean isAir() {
        return true;
    }
}