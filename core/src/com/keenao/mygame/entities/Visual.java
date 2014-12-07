package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Visual extends Entity {
    private Sprite sprite;

    protected Visual(String asset, String type, int positionX, int positionY, int sizeX, int sizeY) {
        super(type, positionX, positionY, sizeX, sizeY);

        this.sprite = new Sprite(AssetManger.getTexture(asset));
        this.setColor(new Color(Color.WHITE));
    }

    public void setAlpha(float alpha) {
        this.sprite.setAlpha(alpha);
    }

    public void setColor(Color color) {
        this.sprite.setColor(color);
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        sprite.setSize(getSizeX(), getSizeY());
        sprite.setPosition(getPositionX(), getPositionY());
        sprite.draw(spriteBatch);
    }
}
