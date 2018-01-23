package com.bobby;

import com.bobby.blocks.BlockSingleTexture;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class TextureManager {

    PImage textureAtlas;

    public final int ATLAS_WIDTH = 16, ATLAS_HEIGHT = 16;
    public final int TEXTURE_SIZE;

    public TextureManager(PApplet applet) {
        textureAtlas = applet.loadImage("terrain.png");
        TEXTURE_SIZE = textureAtlas.width / ATLAS_WIDTH;
    }

    public PImage getTextureAtlas(){
        return this.textureAtlas;
    }

    public PVector getTextureIndex(BlockSingleTexture textureIndex){
        return new PVector(textureIndex.indexX * TEXTURE_SIZE, textureIndex.indexY * TEXTURE_SIZE);
    }
}