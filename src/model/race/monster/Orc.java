package model.race.monster;

public class Orc extends Monster{

    public Orc() {
        super();
        getHealthProperty().set(2);
        getMaxHealthProperty().set(2);
        keyDots.add(5);
        keyDots.add(6);
    }

    @Override
    public String toString() {
        return "Orc";
    }
}
