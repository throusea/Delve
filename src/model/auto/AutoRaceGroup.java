package model.auto;

import controller.GameController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import listener.AutoListener;
import listener.StageListener;
import listener.PanelListener;
import model.dice.Dice;
import model.dice.DiceGroup;
import model.race.Race;
import model.race.RaceGroup;
import model.round.GameStage;

public class AutoRaceGroup implements AutoListener {

    StageListener stageListener;

    PanelListener panelListener;

    protected RaceGroup raceGroup;

    protected DiceGroup diceGroup;

    public AutoRaceGroup(GameController controller, RaceGroup raceGroup) {
        this.stageListener = controller;

        //加一个启动器， autoLauncher, 当它为true时使用；
        this.raceGroup = raceGroup;
        diceGroup = controller.getDiceGroup();
    }

    public void autoRoll() {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(3000));
        pauseTransition.setOnFinished(event -> stageListener.roll());
        pauseTransition.playFromStart();
        PauseTransition pauseTransition1 = new PauseTransition(Duration.millis(5000));
        pauseTransition1.setOnFinished(event -> stageListener.nextStep());
        pauseTransition1.playFromStart();
    }

    public void autoSelect() {
        DiceGroup diceGroup = stageListener.getDiceGroup();
        if(true) {
            Dice dice = diceGroup.getList().get(0);
            Race race = raceGroup.get("");
            panelListener.moveDice(dice, race);
        }
    }

    public void autoAction(GameStage stage) {
        stageListener.actionHandle();
    }

    public void registerListener(StageListener listener) {
        this.stageListener = listener;
    }
}
