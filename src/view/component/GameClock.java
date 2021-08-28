package view.component;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import listener.StageListener;

public class GameClock extends Label implements Runnable{

    Timeline count;

    StageListener stageListener;

    public GameClock() {
        count = new Timeline();
        setFont(Font.font(null, FontWeight.BOLD, 28));

        textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("0")) {
                stageListener.nextStep();
                System.out.println("nextStep");
            }
        });

        KeyValue keyV = new KeyValue(textFillProperty(), Color.BLACK);
        addKeyFrame(new KeyFrame(Duration.ZERO, keyV));
        addKeyFrame(new KeyFrame(Duration.millis(20 * 1000 - 1), keyV));
        keyV = new KeyValue(textFillProperty(), Color.RED);
        addKeyFrame(new KeyFrame(Duration.millis(20 * 1000), keyV));

        for(int i = 60; i >= 0; i--) {
            KeyValue keyValue = new KeyValue(textProperty(), String.valueOf(i));
            addKeyFrame(new KeyFrame(Duration.millis((60 - i) * 1000), keyValue));
        }

        for(int i = 10; i >= 0; i--) {
            KeyValue keyValue = new KeyValue(textProperty(), "");
            addKeyFrame(new KeyFrame(Duration.millis((60 - i) * 1000 + 600) , keyValue));
        }

    }

    public void setCycleCount(int value) {
        count.setCycleCount(value);
    }

    public void addKeyFrame(KeyFrame frame) {
        count.getKeyFrames().add(frame);
    }

    public void registerListener(StageListener listener) {
        stageListener = listener;
    }

    public void start() {
        count.playFromStart();
    }

    public void run() { count.play(); }

    public void pause() { count.pause(); }
}
