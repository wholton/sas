package com.mygdx.game.model.characters;

import com.mygdx.game.model.armors.LeatherHelmet;
import com.mygdx.game.model.characters.attributes.BrawnAttribute;
import com.mygdx.game.model.characters.attributes.CunningAttribute;
import com.mygdx.game.model.characters.attributes.DaringAttribute;
import com.mygdx.game.model.characters.attributes.HeartAttribute;
import com.mygdx.game.model.characters.attributes.SagacityAttribute;
import com.mygdx.game.model.characters.attributes.TenacityAttribute;
import com.mygdx.game.model.characters.cultures.CivilizedCulture;
import com.mygdx.game.model.characters.proficiencies.SwordAndShieldProficiency;

public class Hero extends AbstractCharacter {

  // make builder when ready
  public Hero() {
    super("Delvin", "Male", new CivilizedCulture());
    brawnAttribute = new BrawnAttribute(8);
    heartAttribute = new HeartAttribute(4);
    sagacityAttribute = new SagacityAttribute(4);
    tenacityAttribute = new TenacityAttribute(8);
    cunningAttribute = new CunningAttribute(4);
    daringAttribute = new DaringAttribute(6);
    swordAndShieldProficiency = new SwordAndShieldProficiency(6);
    armors.put("Head", new LeatherHelmet());
  }

}
