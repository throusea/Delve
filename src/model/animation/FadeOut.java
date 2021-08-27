package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeOut extends Animation{

    public FadeOut(Node node, double millis, boolean autoReverse) {
        super(node, millis);

        KeyValue value = new KeyValue(node.opacityProperty(),1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, value));
        value = new KeyValue(node.opacityProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), value));
        timeline.setAutoReverse(autoReverse);
        if(autoReverse) {
            timeline.setCycleCount(4);
        }
    }
    @Override
    public void start() {
        run();
    }

    public void startFrom(double value) {
        KeyValue keyValue = new KeyValue(node.opacityProperty(), value);
        timeline.getKeyFrames().remove(0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, keyValue));
        run();
    }

    @Override
    protected Void call() throws Exception {
        timeline.playFromStart();
        return null;
    }
}
