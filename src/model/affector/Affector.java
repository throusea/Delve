package model.affector;

import javafx.concurrent.Task;
import model.dice.Dice;
import model.race.Race;
import model.race.RaceGroup;
import view.component.DiceComponent;

public abstract class Affector extends Task<Void> {

    RaceGroup raceGroup;

    Race race;

    Dice dice;

    double millis;

//    public Affector(Race race, double millis) {
//        this.race = race;
//        this.millis = millis;
//    }
//
//    public Affector(RaceGroup raceGroup, double millis) {
//        this.raceGroup = raceGroup;
//        this.millis = millis;
//    }
//
//    public Affector(Dice dice, double millis) {
//        this.dice = dice;
//        this.millis = millis;
//    }

    public abstract void start();
}
