package com.keenao.mygame.managers;

import com.badlogic.gdx.utils.TimeUtils;

public class TimeManager extends Manager {
    private long startTime;
    private long lastTick;
    private long elapsedTime;

    @Override
    public void initialize() {
        startTime = getTime();
        lastTick = startTime;
    }

    public void update()
    {
        elapsedTime = TimeUtils.timeSinceMillis(lastTick);
        lastTick = getTime();
    }

    public long getTime()
    {
        return TimeUtils.millis();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
