package model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import model.dice.Dice;
import model.race.Race;
import view.component.DiceComponent;

public abstract class Animation extends Task<Void> {

    Timeline timeline = new Timeline();

    Node node;

    double millis;

    public Animation(Node node, double millis) {
        this.node = node;
        this.millis = millis;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public ObservableList<KeyFrame> getKeyFrames() { return timeline.getKeyFrames(); }

    public abstract void start();
}
