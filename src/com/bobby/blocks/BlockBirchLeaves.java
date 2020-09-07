package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockBirchLeaves extends Block{

    public BlockBirchLeaves(){
        this.name = "Birch Leaves";
        this.texture = new BlockSingleTexture(4,12);
    }

    public boolean isOpaque() {
        return false;
    }
}
