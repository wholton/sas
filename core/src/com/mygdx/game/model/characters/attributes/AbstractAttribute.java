package com.mygdx.game.model.characters.attributes;

public abstract class AbstractAttribute {

  public static final int MAX_SCORE = 8;
  protected final String name;
  protected final String description;
  protected final String examples;
  protected int score;

  public AbstractAttribute(String name, String description, String examples) {
    super();
    this.name = name;
    this.description = description;
    this.examples = examples;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getExamples() {
    return examples;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
