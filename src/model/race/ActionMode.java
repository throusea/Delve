package model.race;

import javafx.concurrent.Task;
import model.race.character.Cleric;
import view.component.RaceComponent;

import java.util.List;

public class ActionMode extends Task<Void> {

    public static final int TO_ENEMY = 1, TO_PARTNER = 2;

    RaceComponent subject, object;

    List<RaceComponent> objects;

    int actionMode;

    public ActionMode(RaceComponent subject, RaceComponent object, int actionMode) {
        this.subject = subject;
        this.object = object;
        this.actionMode = actionMode;
    }

    public ActionMode(RaceComponent subject, List<RaceComponent> objects, int actionMode) {
        this.subject = subject;
        this.objects = objects;
        this.actionMode = actionMode;
    }

    public void action(RaceComponent object) {
        if(subject.hasMarked(object)) return;
        switch (actionMode) {
            case TO_ENEMY: {
                object.setHealth(object.getHealth() - subject.getDamage());
                object.addMark(subject.getKeyMark());
                break;
            }
            case TO_PARTNER: {
                if(subject.isHighlighted() && subject.isReadyAction()) {
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

    @Override
    protected Void call() {
        if(!subject.reduceActionCount()) return null;
        if(objects != null)
            objects.forEach(this::action);
        else
            action(object);
        return null;
    }
}
