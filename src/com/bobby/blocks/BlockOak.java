package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class BlockOak extends Block{

    public BlockOak(){
        this.texture = new BlockSimpleMultiTexture();
        texture.setTOP(5,1);
        texture.setBOTTOM(5,1);
        texture.setSIDES(4,1);
        this.name = "Oak Log";
    }
}
