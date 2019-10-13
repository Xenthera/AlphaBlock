package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSimpleMultiTexture;

public class BlockGrass extends Block{

    public BlockGrass(){
        super();
        this.name = "Grass";
        texture = new BlockSimpleMultiTexture();
        texture.setTOP(0,0);
        texture.setSIDES(3,0);
        texture.setBOTTOM(2,0);
    }
}
