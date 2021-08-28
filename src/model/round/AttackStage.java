package model.round;

import view.sceneShift.StageTabPane;

public class AttackStage extends GameStage{

    @Override
    public void run() {
        super.run();
        StageTabPane.shift(this);
        listener.selectState(true);
        listener.attackState(true);
        listener.autoState();
    }

    @Override
    public String toString() {
        return "AttackStage";
    }
}
