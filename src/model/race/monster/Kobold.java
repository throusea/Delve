package model.race.monster;

public class Kobold extends Monster{

    public Kobold() {
        getHealthProperty().set(3);
        getMaxHealthProperty().set(3);
    }

    @Override
    public String toString() {
        return "Kobold";
    }
}
