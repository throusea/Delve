package model.race.monster;


import model.race.*;
import model.race.action.Action;
import model.race.action.GoalMode;

import java.util.ArrayList;

public class MonsterGroup extends RaceGroup {

    private String diceString;

    public MonsterGroup(Group group) {
        super(group);
        actionList = new ArrayList<>();
    }

    public void setDiceString(String value) {
        diceString = value;
    }

    public void allocateDices() {
        int index = 0;
        for (Race race : raceList) {
            race.getDiceString().set(diceString.substring(index, index + race.getHealth()));
            index += race.getHealth();
            race.diceActionHandle();
            int t = race.getActionCountProperty().intValue();
            while(t-- > 0) {
                actionList.add(new Action(race, race.getActionMode(), GoalMode.SINGLE));
            }
        }
    }

    @Override
    public void resetDiceNum() {
        int totalHealth = 0;
        for (Race race : raceList) {
            totalHealth += race.getHealthProperty().get();
        }
        diceNum = totalHealth;
    }
}
