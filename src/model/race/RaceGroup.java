package model.race;

import javafx.scene.layout.Pane;
import listener.StateChangeListener;
import model.affector.Affector;
import model.affector.Lighter;
import util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

import static util.RandomUtil.nextInt;

public class RaceGroup implements StateChangeListener {

    private List<Race> raceList;

    private Group group;

    private int diceNum;

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

    public int getDiceNum() { return diceNum; }

    public int getSize() {
        return raceList.size();
    }

    public Group getGroup() {
        return group;
    }

    public void setRounding(boolean value) {
        isRounding = value;
    }

    public void setHighlight(boolean value) {
        raceList.forEach(race -> race.setHighlight(value));
    }

    // 点击按钮后出现高亮选择攻击对象，自动计算攻击模式（包括自己和敌人），actionHandle在执行攻击者类里面进行，需要有被攻击者的信息
    public List<Race> actionHandle() {
        List<Race> races = new ArrayList<>();
        raceList.forEach(race -> {
            if(race.diceActionHandle()) races.add(race);
        });
        if(races.isEmpty()) return null;
        return races;
    }

    public void setHealth(int value) {
        raceList.forEach(race -> race.setHealth(value));
    }

    public void reset() {
        raceList.forEach(race -> race.reset());
    }

    @Override
    public boolean isRounding() {
        return isRounding;
    }

    public int getTotalHealth() {
        int totalHealth = 0;
        for (Race race : raceList) {
            totalHealth += race.health.get();
        }
        return totalHealth;
    }
}
