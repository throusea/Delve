package model.race.monster;

public class Giant extends Monster{

    public Giant() {
        setMaxHealth(9);
        setHealth(9);
        hitDots.add(4);
        hitDots.add(5);
        hitDots.add(6);
    }

    @Override
    public String toString() {
        return "Giant";
    }
}
