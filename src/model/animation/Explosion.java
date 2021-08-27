package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Explosion extends Animation{

    public Explosion(Pane pane, double x, double y, double millis) {
        super(pane, millis);

        Circle circle = new Circle(x,y, 1);
        circle.setFill(null);
        circle.setStroke(Color.GOLD);
        KeyValue keyValue;
        pane.getChildren().add(circle);

        System.out.println("explosion");
        keyValue = new KeyValue(circle.scaleXProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, keyValue));
        keyValue = new KeyValue(circle.scaleYProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, keyValue));
        keyValue = new KeyValue(circle.scaleXProperty(), 2000);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        keyValue = new KeyValue(circle.scaleYProperty(), 2000);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(millis), keyValue));
        timeline.getKeyFrames().addAll(new FadeOut(circle, millis, false).getTimeline().getKeyFrames());
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.getChildren().remove(circle);
            }
        });
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
