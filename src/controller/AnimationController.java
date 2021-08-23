package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.animation.Animation;

import java.util.List;

public class AnimationController {
    //FIXME: 放弃中...
    public static Timeline gameTime;
    public AnimationController() {
        gameTime = new Timeline();
        gameTime.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }

    public static void addGameTime(Animation animation) {
        animation.start();
    }

    public static void play() {
        gameTime.play();
    }

    public static void stop() {
        gameTime.stop();
    }
}
