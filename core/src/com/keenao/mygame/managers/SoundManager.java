package com.keenao.mygame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager extends Manager {
    private Music bgm;
    private long bgmId;
    private HashMap<String, Sound> sounds;

    public SoundManager() {
        sounds = new HashMap<>();
    }

    public void playFx(String asset)
    {
        Sound sound = sounds.containsKey(asset) ? sounds.get(asset) : Gdx.audio.newSound(Gdx.files.local("fx_" + asset + ".wav"));
        sounds.put(asset, sound);

        long id = sound.play();
        sound.setVolume(id, 0.4f);
    }

    public void setBgmVolume(float volume)
    {
        // Discard
        if (null == bgm)
        {
            return;
        }

        // Format
        if (volume < 0)
        {
            volume = 0;
        }

        bgm.setVolume(volume * 0.5f);
    }

    public void playBgm(String asset)
    {
        if (null == bgm) {
            bgm = Gdx.audio.newMusic(Gdx.files.local(asset + ".mp3"));
        }
        bgm.play();
        bgm.setLooping(true);
    }

    public void stopAllSounds()
    {
        bgm.stop();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {

    }
}
