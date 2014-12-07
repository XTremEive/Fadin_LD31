package com.keenao.mygame.managers;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class InputManager extends Manager {
    private HashMap<String, Boolean> downKeys;
    private HashMap<String, Integer> keys;

    public InputManager() {
        this.downKeys = new HashMap<>();
        keys = new HashMap<>();
    }

    public InputManager add(String name, Integer key)
    {
        // Add
        keys.put(name, key);

        // Return
        return this;
    }

    public boolean isDown(String key)
    {
        return downKeys.get(key);
    }



    @Override
    public void initialize() {

    }

    @Override
    public void update()
    {
        // Clear states
        downKeys.clear();

        // Refill
        for(Map.Entry<String, Integer> input : keys.entrySet())
        {
            downKeys.put(input.getKey(), Gdx.input.isKeyPressed(input.getValue()));
        }
    }
}
