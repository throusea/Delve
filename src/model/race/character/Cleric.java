package model.race.character;

import model.dice.Dice;
import model.dice.DiceAnalyser;
import model.race.action.Action;
import model.race.action.ActionMode;
import model.race.action.GoalMode;
import util.NextUtil;
import view.component.RaceComponent;

public class Cleric extends Character {

    public static final int MINOR_HEAL = 7, HEAL = 8, MIRACLE = 9;

    int cureMode = 7;

    boolean miracle;

    Dice keyDice;

    public Cleric() {
        super();
        setMaxHealth(5);
        setHealth(5);
        actionMode = ActionMode.TO_PARTNER;
    }

    @Override
    public void setRaceComponent(RaceComponent raceComponent) {
        super.setRaceComponent(raceComponent);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        if(cureMode == DiceAnalyser.analyse(s)) {
            switch (cureMode) {
                case MINOR_HEAL: {
                    addActionCount(2);
                    setCure(1);
                    setPressResponse(true);
                    setDragResponse(true);
                    action = new Action(this, actionMode, GoalMode.SINGLE);
                    break;
                }
                case HEAL: {
                    if(keyDice == null) getRaceComponent().setIsReadyAction(true);
                    else {
                        addActionCount();
                        setCure(keyDice.getDot());
                        setPressResponse(true);
                        action = new Action(this, actionMode, GoalMode.ALL);
                    }
                    break;
                }
                case MIRACLE: {
                    addActionCount();
                    setHighlight(true);
                    setReadyAction(true);
                    setCure(999);
                    setPressResponse(true);
                    action = new Action(this, actionMode, GoalMode.ALL);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public void addCure() {
        addDamage();
    }

    public void setCure(int value) { setDamage(value);}

    public void addCure(int value) {
        addDamage(value);
    }

    public boolean isMiracle() { return miracle; }

    public void setMiracle(boolean miracle) {
        this.miracle = miracle;
    }

    public void setKeyDice(Dice keyDice) {
        this.keyDice = keyDice;
    }

    @Override
    public int getSkillMode() {
        return cureMode;
    }

    @Override
    public String toString() {
        return "Cleric";
    }

    @Override
    public void shiftActionMode() {
        super.shiftActionMode();
        cureMode = NextUtil.next(cureMode,7,9);
    }

    @Override
    public String getSkillString() {
        if(cureMode == MINOR_HEAL) return "minor heal";
        if(cureMode == HEAL) return "heal";
        if(cureMode == MIRACLE) return "miracle";
        return super.getSkillString();
    }
}
