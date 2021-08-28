package model.dice;

import controller.AnimationController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import listener.DiceListener;
import model.animation.DiceDisappear;
import util.RandomUtil;
import view.component.DiceComponent;

public class Dice implements DiceListener {

    public IntegerProperty dot;

    public BooleanProperty checked;

    public DiceComponent diceCpt;

    public Dice() {
        dot = new SimpleIntegerProperty(-1);
        checked = new SimpleBooleanProperty(true);
    }

    public Dice(int dot) {
        this.dot = new SimpleIntegerProperty(dot);
        checked = new SimpleBooleanProperty(true);
    }

    public void roll() {
        if(checked.get()) {
            dot.set(RandomUtil.nextInt(1,6));
            AnimationController.addGameTime(new DiceDisappear(diceCpt, 500, true));
        }
    }

    public Integer getDot() {
        return dot.get();
    }

    public void setDiceCpt(DiceComponent diceCpt) { this.diceCpt = diceCpt; }

    public void setVisible(boolean value) {
        diceCpt.setVisible(value);
    }

    public void clear() {
        dot.set(-1);
        checked.set(true);
    }

    public boolean isEmpty() { return dot == null; }
}
