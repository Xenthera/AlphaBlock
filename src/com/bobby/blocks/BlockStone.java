package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockStone extends Block {

    public BlockStone() {
        super();
        this.name = "Stone";
        texture = new BlockSingleTexture(1,0);
    }
}