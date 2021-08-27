package model.race;

import javafx.beans.property.*;
import listener.RaceListener;
import model.dice.Dice;
import model.race.action.Action;
import model.race.action.ActionMode;
import view.component.RaceComponent;

import java.util.ArrayList;
import java.util.List;

public class Race implements RaceListener {

    private List<Mark> markList;

    RaceComponent raceComponent;

    StringProperty diceString;

    IntegerProperty healthProperty, maxHealthProperty;

    BooleanProperty isDead;

    IntegerProperty actionCountProperty, damageProperty;

    public Action action;

    public ActionMode actionMode = ActionMode.TO_ENEMY;

    public Race() {

        markList = new ArrayList<>();

        diceString = new SimpleStringProperty();

        healthProperty = new SimpleIntegerProperty();
        maxHealthProperty = new SimpleIntegerProperty();

        isDead = new SimpleBooleanProperty();

        actionCountProperty = new SimpleIntegerProperty();

        damageProperty = new SimpleIntegerProperty();
    }

    @Override
    public void setHealth(int value) {
        if(value <= 0) {
            healthProperty.setValue(0);
            isDead.set(true);
        }else healthProperty.set(Math.min(value, maxHealthProperty.get()));
    }

    public void setMaxHealth(int value) {
        maxHealthProperty.set(value);
    }

    public void setDamage(int value) { damageProperty.set(value); }

    public void setActionCount(int value) { actionCountProperty.set(value); }

    public IntegerProperty getHealthProperty() {
        return healthProperty;
    }

    public int getHealth(){ return healthProperty.get(); }

    public IntegerProperty getMaxHealthProperty() { return maxHealthProperty; }

    public IntegerProperty getDamageProperty() { return damageProperty; }

    public int getDamage() { return damageProperty.get(); }

    public IntegerProperty getActionCountProperty() { return actionCountProperty; }

    public StringProperty getDiceString() {
        return diceString;
    }

    public BooleanProperty getDead() { return isDead; }

    public void addDamage() {
        addDamage(1);
    }

    public void addDamage(int value) {
        damageProperty.set(damageProperty.get() + value);
    }

    public void addActionCount(){
        addActionCount(1);
    }

    public void addActionCount(int value) {
        actionCountProperty.set(actionCountProperty.get() + value);
    }

    public void reset() {
        actionCountProperty.set(0);
        damageProperty.set(0);
        clearMark();
        raceComponent.setIsReadyAction(false);
        raceComponent.setPressResponse(false);
        raceComponent.setDragResponse(false);
        diceString.set(null);
    }

    public RaceComponent getRaceComponent() {
        return raceComponent;
    }

    public void setRaceComponent(RaceComponent raceComponent) {
        this.raceComponent = raceComponent;
    }

    public void setHighlight(boolean value) {
        raceComponent.setHighlighted(value);
    }

    public void setReadyAction(boolean value) {
        if(!isDead.get())
        raceComponent.setIsReadyAction(value);
    }

    public void setDragResponse(boolean value) {
        raceComponent.setDragResponse(value);
    }

    public void setPressResponse(boolean value) {
        raceComponent.setPressResponse(value);
    }

    public void setIsDead(boolean value) { isDead.set(value); }

    @Override
    public Race getRace() {
        return this;
    }

    public boolean diceActionHandle() {
        return diceString.get() != null && !isDead.get();
    }

    public int getSkillMode() {
        return actionMode.intValue();
    }

    public void addMark(Mark mark) {
        if(mark != null)
            markList.add(mark);
    }

    public boolean hasMarked(Race race) {
        return markList.contains(getKeyMark());
    }

    public void clearMark() {
        markList.clear();
    }

    public Action getAction() { return action; }

    @Override
    public void setKeyDice(Dice keyDice) {
    }

    @Override
    public Mark getKeyMark() {
        return null;
    }

    public void shiftActionMode() {

    }

    public String getSkillString() {
        return "No mode";
    }

    @Override
    public boolean reduceActionCount() {
        //System.out.println(actionCount);
        if(actionCountProperty.get() == 0) return false;
        addActionCount(-1);
        return true;
    }

    @Override
    public ActionMode getActionMode() { return actionMode; }
}
