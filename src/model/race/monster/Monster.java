package model.race.monster;

import model.race.Race;
import model.race.action.ActionMode;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Race {

    public List<Integer> keyDots;

    public Monster() {
        keyDots = new ArrayList<>();
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
        keyDots = new ArrayList<>();
        getHealthProperty().set(monster.getHealthProperty().get());
        getMaxHealthProperty().set(monster.getMaxHealthProperty().get());
        actionMode = ActionMode.TO_ENEMY;
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        for(int i = 0; i < s.length(); i++) {
            if(keyDots.contains(s.charAt(i) - '0')) addActionCount();
        }
        if(getActionCountProperty().get() > 0) {
            setDragResponse(true);
            return true;
        }
        return false;
    }
}
