package model.affector;

import model.race.Race;
import model.race.RaceGroup;

public class Lighter extends Affector {

    private boolean value = true;

    public Lighter(RaceGroup raceGroup, double millis) {
        this.raceGroup = raceGroup;
        this.millis = millis;
    }

    public Lighter(Race race, boolean value, double millis) {
        this.race = race;
        this.millis = millis;
        this.value = value;
    }

    public Lighter(RaceGroup raceGroup, boolean value, double millis) {
        this.raceGroup = raceGroup;
        this.millis = millis;
        this.value = value;
    }

    @Override
    protected Void call() throws Exception {
        if(raceGroup != null) raceGroup.setHighlight(value);
        if(race != null) race.setHighlight(value);
        return null;
    }

    @Override
    public void start() {
        run();
    }
}
