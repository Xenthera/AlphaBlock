package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockReed extends Block{

    public BlockReed(){
        super();
        this.name = "Reed";
        this.renderType = BlockRenderType.SPRITE;
        this.texture = new BlockSingleTexture(9,4);

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
