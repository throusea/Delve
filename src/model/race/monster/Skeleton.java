package model.race.monster;

public class Skeleton extends Monster{

    public Skeleton() {
        setMaxHealth(1);
        setHealth(1);
        hitDots.add(5);
        hitDots.add(6);
    }

    @Override
    public String toString() {
        return "Skeleton";
    }
}
