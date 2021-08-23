package model.affector;

import model.race.Race;
import model.race.RaceGroup;

public class HealthAffector extends Affector{

    private int value;
    public HealthAffector(RaceGroup raceGroup, int value) {
        this.raceGroup = raceGroup;
        this.value = value;
    }

    public HealthAffector(Race race, int value) {
        this.race = race;
        this.value = value;
    }

    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() throws Exception {
        if(race != null) race.setHealth(value);
        if(raceGroup != null) raceGroup.setHealth(value);
        return null;
    }
}
