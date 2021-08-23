package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.util.Duration;
import model.animation.Animation;
import util.RandomUtil;

public class Quake extends Animation {

    public Quake(Node node, double millis) {
        super(node, millis);

        for(int i = 0; i < millis; i+=10) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i), new KeyValue(node.translateXProperty(), RandomUtil.nextDouble(-10,10))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i), new KeyValue(node.translateYProperty(), RandomUtil.nextDouble(-10,10))));
        }

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), new KeyValue(node.translateXProperty(), 0)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), new KeyValue(node.translateXProperty(), 0)));
    }

    @Override
    public void start() {
        run();
    }

    @Override
    public Void call() {
        timeline.playFromStart();
        return null;
    }
}
