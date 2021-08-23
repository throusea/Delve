package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeOut extends Animation{

    public FadeOut(Node node, double millis, boolean autoReverse) {
        super(node, millis);

        KeyValue value;
        value = new KeyValue(node.opacityProperty(),1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, value));
        value = new KeyValue(node.opacityProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), value));
//        for(double sec = 0; sec <= millis; sec += 10) {
//            KeyValue keyValue = new KeyValue(node.opacityProperty(), 1);
//        }

        timeline.setAutoReverse(autoReverse);
        if(autoReverse) {
            timeline.setCycleCount(4);
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
