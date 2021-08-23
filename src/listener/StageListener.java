package listener;

import model.dice.DiceGroup;

public interface StageListener extends Listener {

    void roll();

    void actionHandle();

    void rollState(boolean value);

    void attackState(boolean value);

    void selectState(boolean value);

    void nextStep();

    void reset();

    DiceGroup getDiceGroup();
}
