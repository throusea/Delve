package model.race.character;

import model.dice.Dice;
import model.dice.DiceAnalyser;
import model.race.action.Action;
import model.race.action.GoalMode;
import util.NextUtil;

public class Wizard extends Character{

    public static final int FIREBALL = 4, CHAIN_LIGHTNING = 5, DEMISE = 6;

    public static final float FIREBALL_SINGLE = 4.1f, FIREBALL_ALL = 4.2f;

    int attackMode = 4;

    float fireballMode = FIREBALL_SINGLE;

    private Dice keyDice;

    public Wizard() {
        super(4);
    }

    public Wizard(int maxHealth) {
        super(maxHealth);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        String s = getDiceString().get();
        if(attackMode == DiceAnalyser.analyse(s)) {
            switch (attackMode) {
                case FIREBALL: {
                    if(fireballMode == FIREBALL_SINGLE) {
                        addActionCount();
                        setDamage(3);
                        setDragResponse(true);
                        action = new Action(this, actionMode, GoalMode.SINGLE);
                    }
                    if(fireballMode == FIREBALL_ALL) {
                        addActionCount();
                        setDamage(1);
                        setPressResponse(true);
                    }
                    break;
                }
                case CHAIN_LIGHTNING: {
                    if(keyDice == null) {
                        setReadyAction(true);
                        return false;
                    } else {
                        addActionCount();
                        setDamage(keyDice.getDot());
                        setPressResponse(true);
                        action = new Action(this, actionMode, GoalMode.ALL);
                    }
                    break;
                }
                case DEMISE: {
                    setReadyAction(true);
                    addActionCount();
                    setDamage(999);
                    setPressResponse(true);
                    action = new Action(this, actionMode, GoalMode.ALL);
                    break;
                }
                default:
            }
            return true;
        }
        return false;
    }

    @Override
    public int getSkillMode() {
        return attackMode;
    }

    @Override
    public void setKeyDice(Dice keyDice) {
        this.keyDice = keyDice;
    }

    @Override
    public String toString() {
        return "Wizard";
    }

    @Override
    public void shiftActionMode() {
        super.shiftActionMode();
        attackMode = NextUtil.next(attackMode,4,6);
    }

    @Override
    public String getSkillString() {
        if(attackMode == FIREBALL) return "Fireball";
        if(attackMode == CHAIN_LIGHTNING) return "Chain lightning";
        if(attackMode == DEMISE) return "Demise";
        return super.getSkillString();
    }
}
