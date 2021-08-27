package model.affector;

import javafx.concurrent.Task;
import model.dice.Dice;
import model.race.Race;
import model.race.RaceGroup;
import view.component.DiceComponent;

public abstract class Affector {

    RaceGroup raceGroup;

    Race race;

    Dice dice;

    protected boolean permanent = false;

    double millis;

    public abstract void start();

    public boolean isPermanent() {
        return permanent;
    }
}
