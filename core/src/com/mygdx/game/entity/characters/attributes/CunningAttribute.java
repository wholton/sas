package com.mygdx.game.entity.characters.attributes;

public final class CunningAttribute extends AbstractAttribute {

  public CunningAttribute(int score) {
    super("Cunning", "Doing stuff others wouldn't want you to do.",
        "Reacting quickly to danger, smelling an ambush, picking a pocket, hiding, sneaking, disgusing yourself, disarming a trap, slipping away.");
    this.score = score;
  }
}
