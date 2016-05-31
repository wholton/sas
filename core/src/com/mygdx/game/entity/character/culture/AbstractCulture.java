package com.mygdx.game.entity.character.culture;

import com.mygdx.game.entity.character.AbstractCharacter;

public abstract class AbstractCulture {

  public final String name;
  public final String description;
  public final String cultureModifiers;

  public AbstractCulture(String name, String description, String cultureModifiers) {
    this.name = name;
    this.description = description;
    this.cultureModifiers = cultureModifiers;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public abstract void applyCultureModifiers(AbstractCharacter character);

}
