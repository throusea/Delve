package model.race;

import javafx.beans.property.*;
import listener.RaceListener;
import model.dice.Dice;
import view.component.RaceComponent;

public class Race implements RaceListener {

    RaceComponent raceComponent;

    StringProperty diceString;

    IntegerProperty health, maxHealth;

    BooleanProperty isDead;

    IntegerProperty actionCount, damage;

    public int basicActionMode = ActionMode.TO_ENEMY;

    public Race() {

        diceString = new SimpleStringProperty();

        health = new SimpleIntegerProperty();
        maxHealth = new SimpleIntegerProperty();

        isDead = new SimpleBooleanProperty();

        actionCount = new SimpleIntegerProperty();

        damage = new SimpleIntegerProperty();
    }

    @Override
    public void setHealth(int value) {
        if(value <= 0) {
            health.setValue(0);
            isDead.set(true);
        }else if(value > maxHealth.get()) health.set(maxHealth.get());
        else health.set(value);
    }

    public void setDamage(int value) { damage.set(value); }

    public IntegerProperty getHealth() {
        return health;
    }

    public IntegerProperty getMaxHealth() { return maxHealth; }

    public IntegerProperty getDamage() { return damage; }

    public IntegerProperty getActionCount() { return actionCount; }

    public StringProperty getDiceString() {
        return diceString;
    }

    public BooleanProperty getDead() { return isDead; }

    public void addDamage() {
        addDamage(1);
    }

    public void addDamage(int value) {
        damage.set(damage.get() + value);
    }

    public void addActionCount(){
        addActionCount(1);
    }

    public void addActionCount(int value) {
        actionCount.set(actionCount.get() + value);
    }

    public void reset() {
        actionCount.set(0);
        damage.set(0);
        raceComponent.setIsReadyAction(false);
        raceComponent.setPressResponse(false);
        raceComponent.setDragResponse(false);
        raceComponent.clearMark();
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

    public boolean diceActionHandle() {
        return diceString.get() != null && !isDead.get();
    }

    public int getActionMode() {
        return basicActionMode;
    }

    @Override
    public void setKeyDice(Dice keyDice) {
    }

    @Override
    public Mark getKeyMark() {
        return null;
    }

    public void shiftActionMode() {

    }

    public String getCurrentMode() {
        return "No mode";
    }

    @Override
    public boolean reduceActionCount() {
        //System.out.println(actionCount);
        if(actionCount.get() == 0) return false;
        addActionCount(-1);
        return true;
    }

    @Override
    public int getBasicActionMode() { return basicActionMode; }
}
