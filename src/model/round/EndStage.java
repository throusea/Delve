package model.round;

public class EndStage extends GameStage{

    @Override
    public void run() {
        super.run();
        listener.selectState(false);
        System.out.println("It is end stage.");
        listener.reset();
        listener.attackState(false);
        listener.autoState();
        listener.gameState();
        listener.nextStep();
    }

    @Override
    public String toString() {
        return "EndStage";
    }
}
