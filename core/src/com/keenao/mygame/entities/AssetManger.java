package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class AssetManger {
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String asset)
    {
        // Maintain
        if (!textures.containsKey(asset))
        {
            textures.put(asset, new Texture(asset + ".png"));
        }

        // Return
        return textures.get(asset);
    }
}
