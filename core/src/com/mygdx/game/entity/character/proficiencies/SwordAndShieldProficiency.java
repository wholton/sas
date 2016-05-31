package com.mygdx.game.entity.character.proficiencies;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mygdx.game.entity.character.maneuver.CutManeuver;
import com.mygdx.game.entity.character.maneuver.ParryManeuver;

public class SwordAndShieldProficiency extends AbstractProficiency {

  public SwordAndShieldProficiency(int score) {
    super(ProficiencyType.SWORD_AND_SHIELD, "Sword and shield style.",
        ImmutableSet.of(new CutManeuver()), ImmutableSet.of(new ParryManeuver()),
        ImmutableMap.of(ProficiencyType.CUT_AND_THRUST, -2));
    this.score = score;
  }

}
