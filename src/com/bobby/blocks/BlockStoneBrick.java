package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockStoneBrick extends Block {

    public BlockStoneBrick() {
        super();
        texture = new BlockSingleTexture(6,3);
    }
}