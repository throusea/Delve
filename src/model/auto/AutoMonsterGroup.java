package model.auto;

import controller.GameController;
import model.race.RaceGroup;
import model.race.monster.MonsterGroup;

public class AutoMonsterGroup extends AutoRaceGroup{

    public AutoMonsterGroup(GameController controller, MonsterGroup monsterGroup) {
        super(controller, monsterGroup);
        controller.currentStation.getAutoLaunch().addListener(((observable, oldValue, newValue) -> {
            if(newValue.intValue() == 0) autoAction();
            if(newValue.intValue() == 1) stageListener.nextStep();
        }));
    }

    @Override
    public void autoAction() {
        autoRoll();
        autoSelect();
    }

    @Override
    public void autoSelect() {
        MonsterGroup monsterGroup = (MonsterGroup) raceGroup;
        monsterGroup.setDiceString(diceGroup.toString());
    }
}
