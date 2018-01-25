package com.bobby.blocks;

import com.bobby.blocks.construction.BlockSingleTexture;

public class BlockStoneBrick extends Block {

    public BlockStoneBrick() {
        super();
        texture = new BlockSingleTexture(6,3);
    }
}