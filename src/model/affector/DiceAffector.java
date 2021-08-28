package model.affector;

import model.dice.DiceGroup;

public class DiceAffector extends Affector{

    private DiceGroup diceGroup;

    private int diceNum;

    public DiceAffector(DiceGroup diceGroup, int diceNum) {
        this.diceGroup = diceGroup;
        this.diceNum = diceNum;
    }

    public void start() {
        int t = diceGroup.getDiceNum() - diceNum;
        while(t-- > 0) {
            diceGroup.removeDice();
        }
    }
}
