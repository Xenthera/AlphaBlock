package com.bobby.blocks;

import com.bobby.blocks.Block;
import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockBedrock extends Block {
    public BlockBedrock() {
        super();
        texture = new BlockSingleTexture(1, 1);
        this.name = "Bedrock";
    }
}
