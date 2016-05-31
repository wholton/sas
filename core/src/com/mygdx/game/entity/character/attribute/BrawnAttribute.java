package com.mygdx.game.entity.character.attribute;

public final class BrawnAttribute extends AbstractAttribute {

  public BrawnAttribute(int score) {
    super("Brawn", "Material power.",
        "Health, breaking down doors, breaking stuff, physical size, looking threatening, moving big heavy stuff.");
    this.score = score;
  }

}
