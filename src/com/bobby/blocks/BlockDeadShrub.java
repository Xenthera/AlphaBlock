package com.bobby.blocks;

import com.bobby.blocks.Block;
import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockDeadShrub extends Block{

    public BlockDeadShrub(){
        super();
        this.name = "Dead Shrub";
        this.renderType = BlockRenderType.SPRITE;
        this.texture = new BlockSingleTexture(7,3);

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
