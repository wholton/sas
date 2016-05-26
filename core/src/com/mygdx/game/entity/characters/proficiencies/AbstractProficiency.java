package com.mygdx.game.entity.characters.proficiencies;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mygdx.game.entity.characters.maneuvers.AbstractManeuver;

public abstract class AbstractProficiency {

  protected final String name;
  protected final String description;
  protected final ImmutableSet<AbstractManeuver> offensiveManeuvers;
  protected final ImmutableSet<AbstractManeuver> defensiveManeuvers;
  protected final ImmutableMap<String, Integer> defaultProficienciesMap;
  public static final int MAX_SCORE = 12;
  public int score;

  // Concrete Proficiencies will pass these values up
  protected AbstractProficiency(String name, String description,
      ImmutableSet<AbstractManeuver> offensiveManeuvers,
      ImmutableSet<AbstractManeuver> defensiveManeuvers,
      ImmutableMap<String, Integer> defaultProficienciesMap) {
    this.name = name;
    this.description = description;
    this.offensiveManeuvers = offensiveManeuvers;
    this.defensiveManeuvers = defensiveManeuvers;
    this.defaultProficienciesMap = defaultProficienciesMap;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ImmutableSet<AbstractManeuver> getOffensiveManeuvers() {
    return offensiveManeuvers;
  }

  public ImmutableSet<AbstractManeuver> getDefensiveManeuvers() {
    return defensiveManeuvers;
  }

  public Map<String, Integer> getDefaultProficiencies() {
    return defaultProficienciesMap;
  }

  public int getScore() {
    return score;
  }

}
