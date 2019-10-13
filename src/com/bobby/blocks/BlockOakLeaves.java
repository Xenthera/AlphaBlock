package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockOakLeaves extends Block{

    public BlockOakLeaves(){
        this.name = "Leaves";
        this.texture = new BlockSingleTexture(4,8);
    }

    public boolean isOpaque() {
        return false;
    }
}
