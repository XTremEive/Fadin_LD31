package com.keenao.mygame.entities.items;

import com.badlogic.gdx.graphics.Color;

public class Block extends Item {
    public Block(String asset, String backgroundAsset, int element, int positionX, int positionY, int sizeX, int sizeY) {
        super(asset, backgroundAsset, "block", element, positionX, positionY, sizeX, sizeY);
    }
}