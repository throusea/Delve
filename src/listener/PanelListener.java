package listener;

import listener.Listener;
import model.dice.Dice;
import model.race.Race;

public interface PanelListener extends Listener {

    void moveDice(Dice dice, Race race);
}
