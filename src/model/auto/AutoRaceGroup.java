package model.auto;

import controller.GameController;
import listener.StageListener;
import listener.PanelListener;
import model.dice.Dice;
import model.dice.DiceGroup;
import model.race.Race;
import model.race.RaceGroup;

import java.util.Timer;
import java.util.TimerTask;

public class AutoRaceGroup {

    StageListener stageListener;

    PanelListener panelListener;

    protected RaceGroup raceGroup;

    protected DiceGroup diceGroup;

    public AutoRaceGroup(GameController controller, RaceGroup raceGroup) {
        this.stageListener = controller;

        //加一个启动器， autoLantcher, 当它为true时使用；
        this.raceGroup = raceGroup;
        diceGroup = controller.getDiceGroup();
    }

    public void autoStage() {
        autoRoll();
        autoSelect();
        autoAction();
    }

    public void autoRoll() {
        stageListener.roll();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stageListener.nextStep();
            }
        }, 3000);
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
