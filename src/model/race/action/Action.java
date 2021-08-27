package model.race.action;

import model.race.Race;
import model.race.RaceGroup;
import model.race.character.Cleric;

public class Action {

    Race subject;

    ActionMode action;
    GoalMode goal;
    public Action(Race subject, ActionMode action, GoalMode goal) {
        this.subject = subject;
        this.action = action;
        this.goal = goal;
    }

    public ActionMode getAction() {
        return action;
    }

    public GoalMode getGoal() {
        return goal;
    }

    public void run(Race object) {
        if(!subject.reduceActionCount()) return;
        handle(object);
    }

    public void run(RaceGroup object) {
        if(!subject.reduceActionCount()) return;
        object.getRaceList().forEach(this::handle);
    }

    public void handle(Race object) {
        if(subject.hasMarked(object)) return;
        switch (action) {
            case TO_ENEMY: {
                object.setHealth(object.getHealth() - subject.getDamage());
                object.addMark(subject.getKeyMark());
                break;
            }
            case TO_PARTNER: {
                if(subject.getSkillMode() == Cleric.MIRACLE) {
                    object.setIsDead(false);
                    object.setHealth(99);
                }
                object.setHealth(object.getHealth() + subject.getDamage());
                object.addMark(subject.getKeyMark());
                break;
            }
            default:
        }
    }
}
