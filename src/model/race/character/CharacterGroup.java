package model.race.character;

import model.race.Group;
import model.race.RaceGroup;

public class CharacterGroup extends RaceGroup {
    public CharacterGroup(Group group) {
        super(group);
    }

    public CharacterGroup(Group human, int diceNum) {
        super(human, diceNum);
    }
}
