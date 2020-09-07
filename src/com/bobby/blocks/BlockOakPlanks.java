package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockOakPlanks extends Block {
    public BlockOakPlanks() {
        super();
        texture = new BlockSingleTexture(4, 0);
        this.name = "Oak Planks";
    }
}
