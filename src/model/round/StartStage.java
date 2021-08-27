package model.round;

public class StartStage extends GameStage {

    @Override
    public void run() {
        listener.addAffector();
        super.run();
        listener.rollState(false);
        listener.selectState(false);
        System.out.println("It is start stage.");
        listener.nextStep();
    }

    @Override
    public String toString() {
        return "StartStage";
    }
}
