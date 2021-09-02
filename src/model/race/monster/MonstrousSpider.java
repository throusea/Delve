package model.race.monster;

public class MonstrousSpider extends Monster{

    Integer stunDot = 1;
    public MonstrousSpider() {
        setMaxHealth(3);
        setHealth(3);
        hitDots.add(5);
        hitDots.add(6);
    }

    @Override
    public String toString() {
        return "MonstrousSpider";
    }
}
