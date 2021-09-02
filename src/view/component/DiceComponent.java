package view.component;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import listener.DiceListener;
import model.animation.DiceAppear;
import model.animation.DiceDisappear;
import model.dice.Dice;

import static util.BorderUtil.createBorder;
import static util.RandomUtil.nextDouble;

public class DiceComponent extends Label {

    private IntegerProperty dot;

    private DiceListener listener;

    private BooleanProperty checked;

    private String s;

    public DiceComponent() {
        dot = new SimpleIntegerProperty();
        checked = new SimpleBooleanProperty(true);
        s = "";
        setPrefSize(50,50);
        setOnMousePressed(evt -> {
            if(dot.get() != -1)
            shiftChecked();
        });
        checked.addListener((observable, oldValue, newValue) -> {
            if(!newValue) s = "A";
            else s = "";
            Image image = new Image("file:src/data/dice/" + dot.get() + s + ".png");
            setGraphic(new ImageView(image));
        });
    }

    public void roll(int dot) {
        Image image = new Image("file:src/data/dice/" + dot + s + ".png");
        Timeline timeline = new DiceDisappear(this, 500, false).getTimeline();
        timeline.setOnFinished(event -> {
            setGraphic(new ImageView(image));
            new DiceAppear(this, 500).start();
        });
        timeline.playFromStart();
    }

    public void setRandPos(double boundX, double boundY) {
        setRandPos(0,0,boundX, boundY);
    }

    public void setRandPos(double posX, double posY, double width, double height) {
        setPos(nextDouble(posX, posX + width), nextDouble(posY, posY + height));
    }

    public void setRandPos(Vec2d loc, Vec2d size) {
        setRandPos(loc.x, loc.y, size.x, size.y);
    }

    public void setPos(Vec2d loc) {
        setPos(loc.x - getPrefWidth(), loc.y - getPrefHeight());
    }

    public void setPos(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setChecked(boolean value) {
        checked.set(value);
    }

    public Integer getDot() {
        return dot.get();
    }

    public boolean getChecked() {
        return checked.get();
    }

    public int getSize() { return (int) this.getBoundsInLocal().getWidth(); }

    public void setSize(int size) { setPrefSize(size, size); }

    public void clear() {
        Image image = new Image("file:src/data/dice/0.png");
        setGraphic(new ImageView(image));
        new DiceAppear(this, 1000).start();
    }

    public void registerListener(Dice listener) {
        this.listener = listener;
        dot.bind(listener.dot);
    }

    private void shiftChecked() {
        checked.set(!checked.get());
    }
}
