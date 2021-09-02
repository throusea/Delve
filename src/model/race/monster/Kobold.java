package model.race.monster;

public class Kobold extends Monster{

    public Kobold() {
        setMaxHealth(1);
        setHealth(1);
        hitDots.add(6);
    }

    @Override
    public String toString() {
        return "Kobold";
    }
}
