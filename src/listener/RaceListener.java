package listener;

import model.dice.Dice;
import model.race.Mark;

public interface RaceListener {

    boolean reduceActionCount();

    int getBasicActionMode();

    void setKeyDice(Dice keyDice);

    void setHealth(int value);

    void setIsDead(boolean value);

    Mark getKeyMark();
}
