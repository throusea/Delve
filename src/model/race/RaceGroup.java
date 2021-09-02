package model.race;

import listener.RaceGroupListener;
import model.race.action.Action;
import view.component.group.RaceGroupComponent;

import java.util.ArrayList;
import java.util.List;

import static util.RandomUtil.nextInt;

public class RaceGroup implements RaceGroupListener {

    public RaceGroupComponent raceGroupComponent;

    public List<Race> raceList;

    public List<Action> actionList;

    private Group group;

    public int diceNum;

    private boolean isRounding;

    public RaceGroup(Group group) {
        raceList = new ArrayList<>();
        this.group = group;
        diceNum = 6;
    }

    public RaceGroup(Group group, int diceNum) {
        raceList = new ArrayList<>();
        this.group = group;
        this.diceNum = diceNum;
    }

    public void add(Race race) {
        raceList.add(race);
    }

    public Race get(String name) {
        for (Race race : raceList) {
            if(race.toString().equals(name)) {
                return race;
            }
        }
        return null;
    }

    public Race get() {
        return raceList.get(nextInt(0, raceList.size() - 1));
    }

    public Race get(int index) {
        return raceList.get(index);
    }

    public int getDiceNum() {
        return diceNum;
    }

    public int getSize() {
        return raceList.size();
    }

    public Group getGroup() {
        return group;
    }

    public List<Race> getRaceList() {
        return raceList;
    }

    public void setRounding(boolean value) {
        isRounding = value;
    }

    public void setHighlight(boolean value) {
        raceList.forEach(race -> race.setHighlight(value));
    }

    public void setHealth(RaceGroup raceGroup) {
        raceList.forEach(race -> race.setHealth(raceGroup.get(race.toString()).getHealth()));
    }

    public void setRaceGroupCpt(RaceGroupComponent raceGroupComponent) {
        this.raceGroupComponent = raceGroupComponent;
    }

    public List<Action> actionHandle() {
        actionList = new ArrayList<>();
        raceList.forEach(race -> {
            if(race.diceActionHandle()) actionList.add(race.getAction());
        });
        if(actionList.isEmpty()) return null;
        return actionList;
    }

    public void setHealth(int value) {
        raceList.forEach(race -> race.setHealth(value));
    }

    public void reset() {
        raceList.forEach(Race::reset);
    }

    public int getTotalHealth() {
        int totalHealth = 0;
        for (Race race : raceList) {
            totalHealth += race.getHealthProperty().get();
        }
        return totalHealth;
    }

    @Override
    public RaceGroup getRaceGroup() {
        return this;
    }

    @Override
    public boolean isRounding() {
        return isRounding;
    }

    public void resetDiceNum() {
    }
}
