package model.round;

import view.sceneShift.StageTabPane;

public class RollStage extends GameStage {

    @Override
    public void run() {
        super.run();
        StageTabPane.shift(this);
        listener.rollState(true);
        listener.attackState(false);
        listener.selectState(false);
    }

    @Override
    public String toString() {
        return "RollStage";
    }
}
