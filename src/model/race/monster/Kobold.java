package model.race.monster;

public class Kobold extends Monster{

    public Kobold() {
        getHealth().set(3);
        getMaxHealth().set(3);
    }

    @Override
    public String toString() {
        return "Kobold";
    }
}
