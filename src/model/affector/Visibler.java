package model.affector;

import model.dice.DiceGroup;

public class Visibler extends Affector{

    private DiceGroup diceGroup;

    private boolean value;

    public Visibler(DiceGroup diceGroup, boolean value) {
        this.diceGroup = diceGroup;
        this.value = value;
    }

    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("VISIS");
        diceGroup.getList().forEach(dice -> {
            dice.setVisible(value);
        });
        return null;
    }
}
