package model.affector;

import model.dice.Dice;
import model.race.Race;
import model.race.RaceGroup;

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
