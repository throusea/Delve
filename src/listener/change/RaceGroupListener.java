package listener.change;

import listener.Listener;
import model.race.RaceGroup;

public interface RaceGroupListener extends Listener {

    RaceGroup getRaceGroup();

    boolean isRounding();
}
