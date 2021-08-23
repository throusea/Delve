package model.race.character;

import model.race.Mark;

public class Fighter extends Character{

    public static final int BASIC_ATTACK = 1;

    public Fighter() {
        super();
        getMaxHealth().set(6);
        getHealth().set(6);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '6') addActionCount();
        }
        if(getActionCount().get() > 0) {
            setDragResponse(true);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        getDamage().set(1);
        getActionCount().set(0);
    }

    @Override
    public int getActionMode() {
        return basicActionMode;
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
    public String getCurrentMode() {
        return "Basic attack";
    }

    @Override
    public Mark getKeyMark() {
        return Mark.FIGHTER_MARK;
    }
}
