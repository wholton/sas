package com.mygdx.game.entity.character.attribute;

public final class HeartAttribute extends AbstractAttribute {

  public HeartAttribute(int score) {
    super("Heart", "Social prowess.",
        "Bluffing, appeasing, begging for your life, getting lucky, getting stuff cheap, telling lies.");
    this.score = score;
  }

}
