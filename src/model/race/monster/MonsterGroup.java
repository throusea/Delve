package model.race.monster;


import com.sun.javafx.geom.Vec2d;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import model.animation.Mover;
import model.race.*;
import model.race.action.Action;
import model.race.action.GoalMode;
import view.component.RaceComponent;

import java.util.ArrayList;

public class MonsterGroup extends RaceGroup {

    private String diceString;

    public MonsterGroup(Group group) {
        super(group);
        actionList = new ArrayList<>();
    }

    public MonsterGroup(Group monster, int diceNum) {
        super(monster, diceNum);
    }

    public void setDiceString(String value) {
        diceString = value;
    }

    public void allocateDices() {
        actionList = new ArrayList<>();
        int index = 0;
        for (Race race : raceList) {
            race.getDiceString().set(diceString.substring(index, index + race.getHealth()));
            index += race.getHealth();
            race.diceActionHandle();
            if(race.getAction() != null) actionList.add(race.getAction());
        }
        System.out.println(actionList);
    }

    public void attack(RaceComponent object) {
        for(Action action: actionList) {
            if(action.getSubject().getActionCountProperty().get() > 0) {
                RaceComponent sub = action.getSubject().getRaceComponent();
                Vec2d init = new Vec2d(sub.getCentX(), sub.getCentY());
                Timeline timeline = new Mover(sub, 200, new Vec2d(object.getCentX(), object.getCentY())).getTimeline();
                timeline.setOnFinished(event -> {
                    action.run(object.getRace());
                    new Mover(sub, 200, init).start();
                });
                timeline.playFromStart();
                break;
            }
        }
    }

    @Override
    public void resetDiceNum() {
        diceNum = getTotalHealth();
    }
}
