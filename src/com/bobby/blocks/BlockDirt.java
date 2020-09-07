package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockDirt extends Block {

    public BlockDirt() {
        super();
        texture = new BlockSingleTexture(2, 0);
        this.name = "Dirt";
    }
}