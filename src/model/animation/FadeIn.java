package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeIn extends Animation{

    public FadeIn(Node node, double millis, boolean autoReverse) {
        super(node, millis);

        KeyValue value;
        value = new KeyValue(node.opacityProperty(),0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, value));
        value = new KeyValue(node.opacityProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), value));

        if(autoReverse) {
            timeline.setCycleCount(2);
            timeline.setAutoReverse(true);
        }
    }
    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() throws Exception {
        timeline.playFromStart();
        return null;
    }
}
