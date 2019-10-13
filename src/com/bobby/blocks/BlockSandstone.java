package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class BlockSandstone extends Block{

    public BlockSandstone(){
        this.texture = new BlockSimpleMultiTexture();
        texture.setTOP(0,11);
        texture.setBOTTOM(0,13);
        texture.setSIDES(0,12);
    }
}
