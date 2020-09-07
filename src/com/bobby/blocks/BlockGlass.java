package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockGlass extends Block{
    public BlockGlass(){
        super();
        this.name = "Glass";

        texture = new BlockSingleTexture(1,3);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isGlassLike() {
        return true;
    }
}
