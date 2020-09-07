package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockWater extends Block{
    public BlockWater(){
        super();
        this.name = "Water";

        texture = new BlockSingleTexture(13,12);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isGlassLike(){
        return true;
    }
}
