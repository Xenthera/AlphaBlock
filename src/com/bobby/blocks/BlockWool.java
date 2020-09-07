package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockWool extends Block{

    public BlockWool(){
        super();
        this.name = "Wool";
        //this.
        if(Math.random() > 0.5) {
            this.texture = new BlockSingleTexture(13, 0);
        }else{
            this.texture = new BlockSingleTexture(12, 0);
        }
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }


}
