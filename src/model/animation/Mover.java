package model.animation;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;
import model.animation.Animation;

public class Mover extends Animation {

    public Mover(Node node, double millis, Vec2d dest) {
        super(node,millis);
        KeyValue keyValue = new KeyValue(node.layoutXProperty(), dest.x);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        keyValue = new KeyValue(node.layoutYProperty(), dest.y);
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
