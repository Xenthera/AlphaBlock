package com.bobby.blocks.texture;

public class BlockSingleTexture extends BlockSimpleMultiTexture{




    public BlockSingleTexture(int indexX, int indexY) {
        super();
        this.setBOTTOM(indexX, indexY);
        this.setSIDES(indexX, indexY);
        this.setTOP(indexX, indexY);
    }
}