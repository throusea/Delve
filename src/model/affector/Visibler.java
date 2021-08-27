package model.affector;

import model.dice.DiceGroup;

public class Visibler extends Affector{

    private DiceGroup diceGroup;

    private boolean value;

    public Visibler(DiceGroup diceGroup, boolean value) {
        this.diceGroup = diceGroup;
        this.value = value;
    }

    public void start(){
        diceGroup.getList().forEach(dice -> dice.setVisible(value));
    }
}
