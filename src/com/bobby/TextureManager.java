package com.bobby;

import com.bobby.blocks.construction.BlockSingleTexture;
import com.bobby.blocks.construction.TextureAtlasPosition;
import processing.core.PApplet;
import processing.core.PImage;

public class TextureManager {

    PImage textureAtlas;

    private int imageSize, individualTextureSize;

    public TextureManager(PApplet applet) {
        textureAtlas = applet.loadImage("terrain.png");
        imageSize = textureAtlas.width;
        individualTextureSize = imageSize/16;
    }

    public PImage getTextureAtlas(){
        return this.textureAtlas;
    }

    public TextureAtlasPosition getTextureIndex(BlockSingleTexture textureIndex){
        float texturePerRow = (float)imageSize / (float)individualTextureSize;
        float indvTexSize = 1.0f / texturePerRow;
        float pixelSize = 1.0f / (float)imageSize;

        float xMin = (textureIndex.indexX * indvTexSize) + 0.0f * pixelSize;
        float yMin = (textureIndex.indexY * indvTexSize) + 0.0f * pixelSize;

        float xMax = (xMin + indvTexSize) - 0.0f * pixelSize;
        float yMax = (yMin + indvTexSize) - 0.0f * pixelSize;
        return new TextureAtlasPosition(xMin, yMin, xMax, yMax);
    }
}