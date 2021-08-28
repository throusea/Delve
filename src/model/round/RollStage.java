package model.round;

import view.sceneShift.StageTabPane;

public class RollStage extends GameStage {

    @Override
    public void run() {
        super.run();
        StageTabPane.shift(this);
        System.out.println("It is the roll stage.");
        listener.rollState(true);
        listener.attackState(false);
        listener.selectState(false);
        listener.autoState();
    }

    @Override
    public String toString() {
        return "RollStage";
    }
}
