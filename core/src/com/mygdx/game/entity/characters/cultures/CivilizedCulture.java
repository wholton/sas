package com.mygdx.game.entity.characters.cultures;

import com.mygdx.game.entity.characters.AbstractCharacter;

public class CivilizedCulture extends AbstractCulture {

  public CivilizedCulture() {
    super("Civilized", "You hail from a civilized culture.",
        "There are no positive or negative aspects for this culture.");
  }

  @Override
  public void applyCultureModifiers(AbstractCharacter character) {
  }

}
