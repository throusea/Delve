package model.auto;

import listener.StageListener;
import listener.PanelListener;
import model.dice.Dice;
import model.dice.DiceGroup;
import model.race.Race;
import model.race.RaceGroup;

public class AutoRaceGroup {

    StageListener stageListener;

    PanelListener panelListener;

    private RaceGroup raceGroup;

    public AutoRaceGroup(RaceGroup raceGroup) {
        this.raceGroup = raceGroup;
    }

    public void autoStage() {
        autoRoll();
        autoSelect();
        autoAction();
    }

    public void autoRoll() {
        stageListener.roll();
        stageListener.getDiceGroup();
        if(false) {
            stageListener.nextStep();
        }
    }

    public void autoSelect() {
        DiceGroup diceGroup = stageListener.getDiceGroup();
        if(true) {
            Dice dice = diceGroup.getList().get(0);
            Race race = raceGroup.get("");
            panelListener.moveDice(dice, race);
        }
    }

    public void autoAction() {
        stageListener.actionHandle();
    }

    public void registerListener(StageListener listener) {
        this.stageListener = listener;
    }
}
