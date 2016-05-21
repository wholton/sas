package com.mygdx.game.model.weapons;

import com.mygdx.game.model.weapons.util.AttackType;

public abstract class AbstractMeleeWeapon extends AbstractWeapon {

  protected final String reach;
  protected final String relatedProficiency;
  protected final String attackType;
  protected final int swingAttackTargetNumber;
  protected final int thrustAttackTargetNumber;
  protected final int swingDamage;
  protected final int thrustDamage;
  protected final int bluntDamage;
  protected final int defenseTargetNumber;

  public AbstractMeleeWeapon(String name, String description, String reach,
      String relatedProficiency, String attackType, int swingAttackTargetNumber,
      int thrustAttackTargetNumber, int swingDamage, int thrustDamage, int bluntDamage,
      int defenseTargetNumber) {
    super(name, description);
    this.reach = reach;
    this.relatedProficiency = relatedProficiency;
    this.attackType = attackType;
    this.swingAttackTargetNumber = swingAttackTargetNumber;
    this.thrustAttackTargetNumber = thrustAttackTargetNumber;
    this.swingDamage = swingDamage;
    this.thrustDamage = thrustDamage;
    this.bluntDamage = bluntDamage;
    this.defenseTargetNumber = defenseTargetNumber;
  }

  public String getRelatedProficiency() {
    return relatedProficiency;
  }

  public int getAttackTargetNumber(String attackType) {
    // if this is not a swing OR thrust weapon and the attack types do not
    // match, throw an error
    if (!AttackType.SWING_OR_THRUST.equals(this.attackType) && !attackType.equals(this.attackType)) {
      throw new RuntimeException(
          "Error: " + name + " (" + this.attackType + ") is not compatible with " + attackType);
    }

    switch (attackType) {
    case AttackType.SWING:
      return swingAttackTargetNumber;
    case AttackType.THRUST:
      return thrustAttackTargetNumber;
    }

    throw new RuntimeException("Error: " + name + " when getting attack type " + attackType);
  }

  public String getAttackType() {
    return attackType;
  }

  public int getDamage(String attackType) {
    // if this is not a swing OR thrust weapon and the attack types do not
    // match, throw an error
    if (!AttackType.SWING_OR_THRUST.equals(this.attackType) && !attackType.equals(this.attackType)) {
      throw new RuntimeException(
          "Error: " + name + " (" + this.attackType + ") is not compatible with " + attackType);
    }

    switch (attackType) {
    case AttackType.SWING:
      return swingDamage;
    case AttackType.THRUST:
      return thrustDamage;
    }

    throw new RuntimeException("Error: " + name + " when getting attack type " + attackType);
  }

  public abstract String getWoundType(String attackType);

}
