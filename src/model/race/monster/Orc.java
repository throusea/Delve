package model.race.monster;

public class Orc extends Monster{

    public Orc() {
        super();
        getHealthProperty().set(2);
        getMaxHealthProperty().set(2);
        hitDots.add(5);
        hitDots.add(6);
    }

    @Override
    public String toString() {
        return "Orc";
    }
}
