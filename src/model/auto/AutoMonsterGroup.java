package model.auto;

import controller.GameController;
import model.race.monster.MonsterGroup;
import model.round.GameStage;
import view.component.RaceComponent;

public class AutoMonsterGroup extends AutoRaceGroup{

    public AutoMonsterGroup(GameController controller, MonsterGroup monsterGroup) {
        super(controller, monsterGroup);
        controller.currentStation.getAutoLaunch().addListener(((observable, oldValue, newValue) -> {
            //暂时弃用
        }));
    }

    @Override
    public void autoAction(GameStage stage) {
        switch (stage.toString()) {
            case "StartStage": {
                break;
            }
            case "RollStage": {
                autoRoll();
                break;
            }
            case "AttackStage": {
                autoSelect();
                isReadyAttack = true;
                break;
            }
            case "EndStage":{
                isReadyAttack = false;
                break;
            }
        }
    }

    @Override
    public void autoSelect() {
        diceGroup = stageListener.getDiceGroup();
        MonsterGroup monsterGroup = (MonsterGroup) raceGroup;
        monsterGroup.setDiceString(diceGroup.toString());
        monsterGroup.allocateDices();
    }

    @Override
    public void attack(RaceComponent object) {
        if(isReadyAttack) {
            MonsterGroup monGroup = (MonsterGroup) raceGroup;
            monGroup.attack(object);
        }
    }
}
