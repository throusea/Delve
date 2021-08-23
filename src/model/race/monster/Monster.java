package model.race.monster;

import model.race.ActionMode;
import model.race.Race;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Race {

    public static List<Monster> monsterList = new ArrayList<>();

    public Monster() {
    }

    public static void initMonsterLibrary() {
    }

    public static Monster get(String name) {
        switch(name) {
            case "Orc": return new Orc();
            case "Kobold": return new Kobold();
            case "MonstrousSpider": return new MonstrousSpider();
            case "BabySpider": return new BabySpider();
            case "Giant": return new Giant();
            case "Skeleton": return new Skeleton();
            //case "Orc": return new Orc();
            //case "Orc": return new Orc();
        }
        return null;
    }

    public Monster(Monster monster) {
        getHealth().set(monster.getHealth().get());
        getMaxHealth().set(monster.getMaxHealth().get());
        basicActionMode = ActionMode.TO_ENEMY;
    }
}
