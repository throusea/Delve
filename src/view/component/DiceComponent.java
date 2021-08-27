package view.component;

import com.sun.javafx.geom.Vec2d;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import listener.DiceListener;
import listener.change.ColorChangeListener;
import listener.change.DotChangeListener;
import model.dice.Dice;

import static util.BorderUtil.createBorder;
import static util.RandomUtil.nextDouble;

public class DiceComponent extends Label {

    private IntegerProperty dot;

    private DiceListener listener;

    private BooleanProperty checked;

    private double x, y;

    public DiceComponent() {
        dot = new SimpleIntegerProperty();
        checked = new SimpleBooleanProperty(true);
        setId("Dice");
        setPrefSize(50,50);
        setText("Dice");
        setOnMousePressed(evt -> {
            if(dot.get() != -1)
            shiftChecked();
        });
        setBorder(createBorder(Color.BLACK));

        dot.addListener(new DotChangeListener(this));
        checked.addListener(new ColorChangeListener(this, Color.WHITE, Color.BLACK));
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

    public Integer getDot() {
        return dot.get();
    }

    public boolean getChecked() {
        return checked.get();
    }

    public int getSize() { return (int) this.getBoundsInLocal().getWidth(); }

    public void setSize(int size) { setPrefSize(size, size); }

    public void registerListener(Dice listener) {
        this.listener = listener;
        dot.bind(listener.dot);
        checked.bindBidirectional(listener.checked);
    }

    private void shiftChecked() {
        checked.set(!checked.get());
    }
}
