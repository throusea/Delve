package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class Balloon extends Animation{

    public Balloon(Node node, double millis, double value) {
        super(node,millis);

        KeyValue keyValue;

        keyValue = new KeyValue(node.scaleXProperty(), value);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        keyValue = new KeyValue(node.scaleYProperty(), value);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
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
