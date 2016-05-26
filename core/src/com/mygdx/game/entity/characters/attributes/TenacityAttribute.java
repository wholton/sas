package com.mygdx.game.entity.characters.attributes;

public final class TenacityAttribute extends AbstractAttribute {

  public TenacityAttribute(int score) {
    super("Tenacity", "Will power and dedication.",
        "Recuperating, shrugging off a spell, crafting stuff, knowing stuff related to your passions, taking the pain.");
    this.score = score;
  }

}
