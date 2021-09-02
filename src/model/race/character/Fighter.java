package model.race.character;

import model.race.action.Action;
import model.race.Mark;
import model.race.action.ActionMode;
import model.race.action.GoalMode;

public class Fighter extends Character{

    public static final int BASIC_ATTACK = 1;

    public Fighter() {
        super(6);
    }

    public Fighter(int maxHealth) {
        super(maxHealth);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '6') addActionCount();
        }
        if(getActionCountProperty().get() > 0) {
            setDragResponse(true);
            action = new Action(this, ActionMode.TO_ENEMY, GoalMode.SINGLE);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        getDamageProperty().set(1);
        getActionCountProperty().set(0);
    }

    @Override
    public int getSkillMode() {
        return actionMode.intValue();
    }

    @Override
    public String toString() {
        return "Fighter";
    }

    @Override
    public void shiftActionMode() {
        super.shiftActionMode();
    }

    @Override
    public String getSkillString() {
        return "Basic attack";
    }

    @Override
    public Mark getKeyMark() {
        return Mark.FIGHTER_MARK;
    }
}
