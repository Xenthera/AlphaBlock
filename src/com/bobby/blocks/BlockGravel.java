package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockGravel extends Block {
    public BlockGravel() {
        super();
        texture = new BlockSingleTexture(3, 1);
        this.name = "Gravel";
    }
}
