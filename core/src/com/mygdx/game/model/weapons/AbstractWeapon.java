package com.mygdx.game.model.weapons;

public abstract class AbstractWeapon {

  protected final String name;
  protected final String description;

  public AbstractWeapon(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

}
