package model.race.monster;

public class Orc extends Monster{

    public Orc() {
        getHealth().set(2);
        getMaxHealth().set(2);
    }

    @Override
    public String toString() {
        return "Orc";
    }
}
