package com.mygdx.game.entity.weapon;

import com.mygdx.game.entity.characters.proficiencies.util.ProficiencyType;

public class ShortSword extends AbstractMeleeWeapon {

  public ShortSword() {
    super(WeaponType.SHORT_SWORD, "A sword with a short, two-edged blade.", ReachType.SHORT,
        ProficiencyType.SWORD_AND_SHIELD, AttackType.SWING_OR_THRUST, 6, 6, 0, 0, -2, 7);
  }

  @Override
  public String getWoundType(String attackType) {
    switch (attackType) {
    case AttackType.SWING:
      return DamageType.CLEAVING;
    case AttackType.THRUST:
      return DamageType.PIERCING;
    }
    throw new RuntimeException("ERROR: ");
  }

}
