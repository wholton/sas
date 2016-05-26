package com.mygdx.game.entity.characters.maneuvers;

public abstract class AbstractManeuver {

  protected final String name;
  protected final String description;
  protected final int minimumProficiency;

  public AbstractManeuver(String name, String description, int minimumProficiency) {
    super();
    this.name = name;
    this.description = description;
    this.minimumProficiency = minimumProficiency;
  }

}
