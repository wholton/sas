package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class FPSLogger {

  private long startTime;
  private int threshold;
  
  public FPSLogger(int threshold) {
    startTime = TimeUtils.nanoTime();
    this.threshold = threshold;
  }
  
  /** Logs the current frames per second to the console. */
  public void log() {
    if (TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */{
      Gdx.app.log("FPSLogger", "fps: " + Gdx.graphics.getFramesPerSecond());
      startTime = TimeUtils.nanoTime();
    }
  }
  
  /** Logs the current frames per second to the console if they're above the threshold. */
  public void logBelowThreshold() {
    if (Gdx.graphics.getFramesPerSecond() < threshold && TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */{
      Gdx.app.log("FPSLogger", "fps: " + Gdx.graphics.getFramesPerSecond());
      startTime = TimeUtils.nanoTime();
    }
  }
  
}
