package com.bobby.blocks;

public class BlockAir extends Block {

    public BlockAir() {
        super();
    }

    public boolean isSolid() {
        return false;
    }
    public boolean isOpaque() { return false; }
}