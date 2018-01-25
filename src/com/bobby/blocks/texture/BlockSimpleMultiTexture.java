package com.bobby.blocks.texture;

public class BlockSimpleMultiTexture {
    public int[] TOP, SIDES, BOTTOM;

    public BlockSimpleMultiTexture(){
        TOP = new int[]{0,0};
        SIDES = new int[]{0,0};
        BOTTOM = new int[]{0,0};
    }

    public void setTOP(int x, int y){
        this.TOP[0] = x;
        this.TOP[1] = y;
    }
    public void setBOTTOM(int x, int y){
        this.BOTTOM[0] = x;
        this.BOTTOM[1] = y;
    }
    public void setSIDES(int x, int y){
        this.SIDES[0] = x;
        this.SIDES[1] = y;
    }
}
