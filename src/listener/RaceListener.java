package listener;

import model.dice.Dice;
import model.race.Mark;
import model.race.Race;
import model.race.action.ActionMode;

public interface RaceListener {

    boolean reduceActionCount();

    ActionMode getActionMode();

    void setKeyDice(Dice keyDice);

    void setHealth(int value);

    void setIsDead(boolean value);

    Race getRace();

    Mark getKeyMark();
}
