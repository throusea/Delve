package model.race.monster;

import model.race.Race;
import model.race.action.Action;
import model.race.action.ActionMode;
import model.race.action.GoalMode;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Race {

    public List<Integer> hitDots;

    public Monster() {
        hitDots = new ArrayList<>();
    }

    public static Monster get(String name) {
        switch(name) {
            case "Orc": return new Orc();
            case "Kobold": return new Kobold();
            case "MonstrousSpider": return new MonstrousSpider();
            case "BabySpider": return new BabySpider();
            case "Giant": return new Giant();
            case "Skeleton": return new Skeleton();
            case "Dragon": return new Dragon();
            //case "Orc": return new Orc();
            //case "Orc": return new Orc();
        }
        return null;
    }

    public Monster(Monster monster) {
        hitDots = new ArrayList<>();
        getHealthProperty().set(monster.getHealthProperty().get());
        getMaxHealthProperty().set(monster.getMaxHealthProperty().get());
        actionMode = ActionMode.TO_ENEMY;
        setDamage(1);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        for(int i = 0; i < s.length(); i++) {
            if(hitDots.contains(s.charAt(i) - '0')) {
                addActionCount();
                action = new Action(this, getActionMode(), GoalMode.SINGLE);
            }
        }
        if(getActionCountProperty().get() > 0) {
            setDragResponse(true);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        setDamage(1);
    }
}
