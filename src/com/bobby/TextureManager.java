package com.bobby;

import com.bobby.blocks.texture.TextureAtlasPosition;
import processing.core.PApplet;
import processing.core.PImage;

public class TextureManager {

    public PImage textureAtlas;

    private int imageSize, individualTextureSize;

    public TextureManager(PApplet applet) {
        textureAtlas = applet.loadImage("terrain2.png");
        imageSize = textureAtlas.width;
        individualTextureSize = imageSize/16;
    }

    public PImage getTextureAtlas(){
        return this.textureAtlas;
    }

    public TextureAtlasPosition getTextureIndex(int[] pos){
        float texturePerRow = (float)imageSize / (float)individualTextureSize;
        float indvTexSize = 1.0f / texturePerRow;
        float pixelSize = 1.0f / (float)imageSize;

        float xMin = (pos[0] * indvTexSize) + 0.0f * pixelSize;
        float yMin = (pos[1] * indvTexSize) + 0.0f * pixelSize;

        float xMax = (xMin + indvTexSize) - 0.0f * pixelSize;
        float yMax = (yMin + indvTexSize) - 0.0f * pixelSize;
        return new TextureAtlasPosition(xMin, yMin, xMax, yMax);
    }
}