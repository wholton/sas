package com.mygdx.game.entity.armor;

public abstract class AbstractArmor {

  protected final String targetZone;
  protected final int score;

  public AbstractArmor(String targetZone, int score) {
    this.targetZone = targetZone;
    this.score = score;
  }

  public int getScore() {
    return score;
  }

}
