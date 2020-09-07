package com.bobby.blocks;

import com.bobby.blocks.Block;
import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class BlockBirch extends Block{

    public BlockBirch(){
        this.texture = new BlockSimpleMultiTexture();
        texture.setTOP(5,1);
        texture.setBOTTOM(5,1);
        texture.setSIDES(5,7);
        this.name = "Birch Log";
    }
}
