package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockSand extends Block{
    public BlockSand(){
        super();
        this.name = "Sand";
        texture = new BlockSingleTexture(2,1);
    }
}
