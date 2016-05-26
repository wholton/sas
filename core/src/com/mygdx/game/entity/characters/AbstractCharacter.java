package com.mygdx.game.entity.characters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mygdx.game.DiceRoller;
import com.mygdx.game.DiceRoller.DieSize;
import com.mygdx.game.entity.armor.AbstractArmor;
import com.mygdx.game.entity.characters.attributes.AbstractAttribute;
import com.mygdx.game.entity.characters.cultures.AbstractCulture;
import com.mygdx.game.entity.characters.proficiencies.AbstractProficiency;
import com.mygdx.game.entity.characters.proficiencies.util.ProficiencyType;
import com.mygdx.game.entity.weapon.AbstractMeleeWeapon;
import com.mygdx.game.entity.weapon.WoundType;

public class AbstractCharacter {

  protected final String name;
  protected final String sex;

  public static final int DEFAULT_LOOT = 2;
  protected int loot;

  protected final AbstractCulture culture;

  protected AbstractAttribute brawnAttribute;
  protected AbstractAttribute cunningAttribute;
  protected AbstractAttribute daringAttribute;
  protected AbstractAttribute heartAttribute;
  protected AbstractAttribute sagacityAttribute;
  protected AbstractAttribute tenacityAttribute;

  protected AbstractProficiency brawlingProficiency;
  protected AbstractProficiency cutAndThrustProficiency;
  protected AbstractProficiency daggerProficiency;
  protected AbstractProficiency greatswordProficiency;
  protected AbstractProficiency longswordProficiency;
  protected AbstractProficiency massWeaponAndShieldProficiency;
  protected AbstractProficiency missleWeaponProficiency;
  protected AbstractProficiency polearmProficiency;
  protected AbstractProficiency spearAndShieldProficiency;
  protected AbstractProficiency swordAndShieldProficiency;
  protected AbstractProficiency wrestlingProficiency;

  protected int currentMeleePool;
  protected String currentStance;

  protected List<AbstractMeleeWeapon> weapons;
  protected AbstractMeleeWeapon currentWeapon;
  protected final Map<String, AbstractArmor> armors;

  protected int shock;
  protected int bloodLoss;
  protected int pain;
  protected boolean isDead;

  public AbstractCharacter(String name, String sex, AbstractCulture culture) {
    this.name = name;
    this.sex = sex;
    this.culture = culture;
    loot = DEFAULT_LOOT;
    armors = new HashMap<String, AbstractArmor>();
  }

  public void readyForCombat() {
    currentMeleePool = getMaxMeleePool();
    shock = 0;
  }

  public void attack(AbstractCharacter target, String targetZone, String attackType,
      int expendedMeleePool) {
    expendMeleePool(expendedMeleePool);
    final int successes = attemptHit(attackType, expendedMeleePool);
    if (successes > 0) {
      // hit
      target.applyHit(targetZone, attackType, currentWeapon.getWoundType(attackType),
          calculateImpactRating(attackType, successes));
    } else {
      // miss
      System.out.println("MISS!");
    }

  }

  private void expendMeleePool(int expendedMeleePool) {
    if (currentMeleePool - expendedMeleePool < 0) {
      throw new RuntimeException("You can't go below 0 melee pool!");
    }
    currentMeleePool -= expendedMeleePool;
  }

  private int attemptHit(String attackType, int expendedMeleePool) {
    final int attackTargetNumber = currentWeapon.getAttackTargetNumber(attackType);
    final List<Integer> rolls = (new DiceRoller()).roll(DieSize.D12, expendedMeleePool);
    System.out.println(rolls + " VS " + attackTargetNumber);
    return (int) rolls.stream().filter(roll -> roll >= attackTargetNumber).count();
  }

  private int calculateImpactRating(String attackType, int successes) {
    return successes + (brawnAttribute.getScore() / 2) + currentWeapon.getDamage(attackType);
  }

  private int applyHit(String targetZone, String attackType, String damageType, int impactRating) {
    final int armorRating = armors.get(targetZone) == null ? 0 : armors.get(targetZone).getScore();
    final int woundLevel = impactRating - (brawnAttribute.getScore() / 2) - armorRating;

    switch (currentWeapon.getWoundType(attackType)) {
    case WoundType.CLEAVING:
      applyCleavingDamage(targetZone, woundLevel);
      break;
    case WoundType.PIERCING:
      applyPiercingDamage(targetZone, woundLevel);
      break;
    case WoundType.BLUNT:
      applyBluntDamage(targetZone, woundLevel);
      break;
    case WoundType.GENERIC:
      applyGenericDamage(targetZone, woundLevel);
      break;
    case WoundType.SWUNG_PIERCING:
      applySwungPiercingDamage(targetZone, woundLevel);
      break;
    }

    return woundLevel;
  }

  private void applyCleavingDamage(String targetZone, int woundLevel) {
    pain = woundLevel;
  }

  private void applyPiercingDamage(String targetZone, int woundLevel) {
    pain = -woundLevel;
  }

  private void applyBluntDamage(String targetZone, int woundLevel) {
    pain = -10 * woundLevel;
  }

  private void applySwungPiercingDamage(String targetZone, int woundLevel) {
    pain = -100 * woundLevel;
  }

  private void applyGenericDamage(String targetZone, int woundLevel) {
    pain = -1000 * woundLevel;
  }

  public int getReflex() {
    return Math.round((cunningAttribute.getScore() + daringAttribute.getScore()) / 2);
  }

  public int getAim() {
    return Math.round((sagacityAttribute.getScore() + cunningAttribute.getScore()) / 2);
  }

  public int getKnockdown() {
    return Math.round((brawnAttribute.getScore() + daringAttribute.getScore()) / 2);
  }

  public int getKnockout() {
    return Math.round((brawnAttribute.getScore() + tenacityAttribute.getScore()) / 2);
  }

  public int getMove() {
    return Math.round(
        (brawnAttribute.getScore() + daringAttribute.getScore() + cunningAttribute.getScore()) / 3);
  }

  public int getMaxMeleePool() {
    return getReflex() + getCurrentWeaponProficiency() - shock;
  }

  public int getCurrentWeaponProficiency() {
    if (currentWeapon == null) {
      return brawlingProficiency.getScore();
    }
    switch (currentWeapon.getRelatedProficiency()) {
    case ProficiencyType.BRAWLING:
      return brawlingProficiency.getScore();
    case ProficiencyType.CUT_AND_THRUST:
      return cutAndThrustProficiency.getScore();
    case ProficiencyType.SWORD_AND_SHIELD:
      return swordAndShieldProficiency.getScore();
    }
    return 0;
  }

  /*
   * Getters for abstract character fields
   */

  public void setCurrentWeapon(AbstractMeleeWeapon weapon) {
    currentWeapon = weapon;
  }

  public int getCurrentMeleePool() {
    return currentMeleePool;
  }

  public int getPain() {
    return pain;
  }

}
