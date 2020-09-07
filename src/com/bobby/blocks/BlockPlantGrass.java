package com.bobby.blocks;

import com.bobby.blocks.texture.BlockSingleTexture;

public class BlockPlantGrass extends Block{

    public BlockPlantGrass(){
        super();
        this.name = "Grass";
        this.renderType = BlockRenderType.SPRITE;
        this.texture = new BlockSingleTexture(7, 2);

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
