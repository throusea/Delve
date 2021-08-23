package model.race.character;

import model.dice.Dice;
import model.dice.DiceAnalyser;
import util.NextUtil;

public class Rogue extends Character{

    public static final int SNEAK_ATTACK = 2, CRIPPLING_STRIKE = 3;

    int attackMode = SNEAK_ATTACK;

    private Dice keyDice;

    public Rogue() {
        super();
        getMaxHealth().set(4);
        getHealth().set(4);
    }

    @Override
    public boolean diceActionHandle() {
        if(!super.diceActionHandle()) return false;
        if(attackMode == SNEAK_ATTACK) {
            String s = getDiceString().get();
            int damage = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') damage++;
            }

            if(damage > 0) {
                setDamage(damage);
                setDragResponse(true);
                return true;
            }
        }
        if(attackMode == CRIPPLING_STRIKE) {
            String s = getDiceString().get();
            if(attackMode == DiceAnalyser.analyse(s)) {
                if (keyDice != null) {
                    setDamage(keyDice.getDot());
                    setDragResponse(true);
                    return true;
                } else {
                    System.out.println("KeyDice!");
                    getRaceComponent().setIsReadyAction(true);
                }
            }
        }
        return false;
    }
    @Override
    public void reset() {
        super.reset();
        setDamage(0);
        getActionCount().set(1);
    }

    @Override
    public int getActionMode() {
        return attackMode;
    }

    @Override
    public void setKeyDice(Dice keyDice) {
        this.keyDice = keyDice;
    }

    @Override
    public String toString() {
        return "Rogue";
    }

    @Override
    public void shiftActionMode() {
        super.shiftActionMode();
        attackMode = NextUtil.next(attackMode,2,3);
    }

    @Override
    public String getCurrentMode() {
        if(attackMode == SNEAK_ATTACK) return "Sneak attack";
        if(attackMode == CRIPPLING_STRIKE) return "Crippling strike";
        return super.getCurrentMode();
    }
}
