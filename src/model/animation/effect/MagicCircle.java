package model.animation.effect;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.animation.Balloon;

public class MagicCircle {

    ParallelTransition paraTrans;
    public MagicCircle(Pane pane, double x, double y) {
        Image bigCircle = new Image("file:src/image/magic/1.png");
        Image smallCircle = new Image("file:src/image/magic/2.png");
        Label label1 = new Label("", new ImageView(bigCircle));
        Label label2 = new Label("", new ImageView(smallCircle));
        label1.setScaleX(0);
        label1.setScaleY(0);
        label2.setScaleX(0);
        label2.setScaleY(0);
        label1.setTranslateX(x - bigCircle.getWidth()/2);
        label1.setTranslateY(y - bigCircle.getHeight()/2);
        label2.setTranslateX(x - smallCircle.getWidth()/2);
        label2.setTranslateY(y - smallCircle.getHeight()/2);

        RotateTransition rotateTrans1 = new RotateTransition(Duration.millis(2000), label1);
        RotateTransition rotateTrans2 = new RotateTransition(Duration.millis(2000), label2);
        rotateTrans1.setByAngle(60);
        rotateTrans2.setByAngle(-60);

        paraTrans = new ParallelTransition();
        paraTrans.getChildren().addAll(
                rotateTrans1,
                rotateTrans2,
                new Balloon(label1, 150, 1).getTimeline(),
                new Balloon(label2, 150,1).getTimeline()
        );
        paraTrans.setOnFinished(event -> {
            new Balloon(label1, 150, 0).start();
            new Balloon(label2, 150, 0).start();
        });

        pane.getChildren().addAll(label1, label2);
    }

    public void play() {
        paraTrans.playFromStart();
    }

    public Transition getTransition() {
        return paraTrans;
    }
}
