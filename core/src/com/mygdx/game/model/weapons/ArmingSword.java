package com.mygdx.game.model.weapons;

import com.mygdx.game.model.characters.proficiencies.util.ProficiencyType;
import com.mygdx.game.model.weapons.util.AttackType;
import com.mygdx.game.model.weapons.util.DamageType;
import com.mygdx.game.model.weapons.util.ReachType;
import com.mygdx.game.model.weapons.util.WeaponType;

public final class ArmingSword extends AbstractMeleeWeapon {

  public ArmingSword() {
    super(WeaponType.ARMING_SWORD, "The basic, run-of-the-mill one-handed sword.", ReachType.MEDIUM,
        ProficiencyType.SWORD_AND_SHIELD, AttackType.SWING_OR_THRUST, 7, 7, 1, 1, -1, 7);
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
