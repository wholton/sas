package com.mygdx.game.entity.characters.attributes;

public final class DaringAttribute extends AbstractAttribute {

  public DaringAttribute(int score) {
    super("Daring", "Doing stuff that takes lots of guts.",
        "Fighting, climbing, acrobatics, showing off, facing down something big and nasty.");
    this.score = score;
  }

}
