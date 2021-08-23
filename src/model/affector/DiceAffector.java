package model.affector;

import model.dice.DiceGroup;

public class DiceAffector extends Affector{
    private DiceGroup diceGroup;

    private int diceNum;

    public DiceAffector(DiceGroup diceGroup, int diceNum) {
        this.diceGroup = diceGroup;
        this.diceNum = diceNum;
    }

    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() throws Exception {
        int t = diceGroup.getDiceNum() - diceNum;
        while(diceNum-- > 0) {
            diceGroup.getList().remove(0);
        }
        return null;
    }
}
