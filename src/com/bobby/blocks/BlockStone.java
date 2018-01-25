package com.bobby.blocks;

import com.bobby.blocks.construction.BlockSingleTexture;

public class BlockStone extends Block {

    public BlockStone() {
        super();
        texture = new BlockSingleTexture(1,0);
    }
}