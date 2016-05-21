package com.mygdx.game.data;

public class GameProgress {
  // TODO: Not currently using this, just a demo.

  private static GameProgress Instance;

  public static GameProgress getInstance() {
    if (Instance == null) {
      Instance = new GameProgress();
    }
    return Instance;
  }

  private GameProgress() {
    // load if it is there
  }

}
