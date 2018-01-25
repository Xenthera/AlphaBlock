package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockOakLeaves extends Block{

    public BlockOakLeaves(){
        this.texture = new BlockSingleTexture(4,3);
    }

    public boolean isOpaque() {
        return false;
    }
}
