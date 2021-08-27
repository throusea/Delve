package model.affector;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
        System.out.println("start!");
    }
}
