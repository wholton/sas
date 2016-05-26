package com.mygdx.game.entity.characters.attributes;

public final class SagacityAttribute extends AbstractAttribute {

  public SagacityAttribute(int score) {
    super("Sagacity", "Knowledge and understanding.",
        "Learning, spotting something far away, awareness, flashes of limelight, examing stuff, knowing esoteric or random stuff, seeing through someone's lies.");
    this.score = score;
  }

}
