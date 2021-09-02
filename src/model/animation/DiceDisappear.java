package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import model.animation.Animation;
import view.component.DiceComponent;

public class DiceDisappear extends Animation {

    public DiceDisappear(DiceComponent diceComponent, double millis, boolean autoReverse) {
        super(diceComponent, millis);

        KeyValue keyValue;
        for(int i = 0; i * 100 < millis; i ++) {
            keyValue = new KeyValue(diceComponent.rotateProperty(), i * 60);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), keyValue));
        }
        keyValue = new KeyValue(diceComponent.scaleXProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, keyValue));
        keyValue = new KeyValue(diceComponent.scaleYProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, keyValue));

        keyValue = new KeyValue(diceComponent.scaleXProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        keyValue = new KeyValue(diceComponent.scaleYProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        timeline.setAutoReverse(autoReverse);
        if(autoReverse) timeline.setCycleCount(2);
    }

    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() {
        timeline.playFromStart();
        return null;
    }
}
