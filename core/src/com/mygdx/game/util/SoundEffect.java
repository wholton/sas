package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.SaSGame;

public class SoundEffect {

  /** The sound effect. */
  protected Sound sound;
  
  /** 
   * The id for the currently playing sound effect. Should be -1 if not 
   * currently being played. 
   **/
  protected long footstepSoundId;
  
  protected float volumeScalar;
  
  public SoundEffect(Sound sound, float duration, float volumeScalar) {
    this.sound = sound;
    this.volumeScalar = volumeScalar;
    footstepSoundId = -1;
  }
  
  public void play() {
    if (footstepSoundId == -1) {
      footstepSoundId = sound.play(((SaSGame) Gdx.app.getApplicationListener())
          .getGamePreferences().getAdjustedSoundVolume() * volumeScalar);
    } else {
      sound.resume(footstepSoundId);
    }
  }
  
  public void stop() {
    sound.stop(footstepSoundId);
    footstepSoundId = -1;
  }
  
  public void pause() {
    sound.pause(footstepSoundId);
  }
  
  public void loop() {
    play();
    sound.setLooping(footstepSoundId, true);
  }
  
}