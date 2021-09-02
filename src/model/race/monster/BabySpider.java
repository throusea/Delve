package model.race.monster;

public class BabySpider extends Monster{

    Integer stunDot = 1;
    public BabySpider() {
        setMaxHealth(2);
        setHealth(2);
        hitDots.add(5);
        hitDots.add(6);
    }
    @Override
    public String toString() {
        return "BabySpider";
    }
}
