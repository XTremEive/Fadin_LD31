package com.keenao.mygame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keenao.mygame.managers.InputManager;
import com.keenao.mygame.managers.SoundManager;
import com.keenao.mygame.managers.TimeManager;

public class Timer extends Entity {

    private long remainingTime;
    private long maxTime;

    public Timer(int maxTime) {
        super("timer", -1, -1, 0, 0);

        this.maxTime = maxTime;
    }

    public Timer increaseRemainingTime(long by)
    {
        // Set
        setRemainingTime(getRemainingTime() + by);

        // Return
        return this;
    }

    public Timer decreaseRemainingTime(long by)
    {
        return increaseRemainingTime(-by);
    }

    public long getMaxTime() {
        return maxTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        // Set
        this.remainingTime = remainingTime;

        // Format
        if (this.remainingTime > maxTime)
        {
            this.remainingTime = maxTime;
        }
    }

    @Override
    public void update(InputManager inputManager, TimeManager timeManager, SoundManager soundManager) {
        decreaseRemainingTime(timeManager.getElapsedTime());
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }
}
