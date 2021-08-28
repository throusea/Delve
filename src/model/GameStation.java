package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import listener.StageListener;
import model.affector.Affector;
import model.affector.DiceAffector;
import model.affector.Lighter;
import model.affector.Visibler;
import model.dice.DiceGroup;
import model.race.*;
import model.race.action.Action;
import model.race.action.ActionMode;
import model.round.*;
import util.NextUtil;

import java.util.ArrayList;
import java.util.List;

import static model.round.GameRound.END;
import static model.round.GameRound.START;

public class GameStation {

    List<GameRound> roundList;

    List<Affector> affectorList;

    List<RaceGroup> raceGroupList;

    GameEnvironment environment;

    StageListener listener;

    int currentRound;

    IntegerProperty autoLaunch;

    public GameStation() {
        roundList = new ArrayList<>();
        affectorList = new ArrayList<>();
        raceGroupList = new ArrayList<>();
        autoLaunch = new SimpleIntegerProperty(0);
    }

    public DiceGroup getDiceGroup(int index) { return roundList.get(index).getDiceGroup(); }

    public DiceGroup getDiceGroup() { return roundList.get(currentRound).getDiceGroup(); }

    public RaceGroup getRaceGroup(int index) {
        return raceGroupList.get(index);
    }

    public GameRound getRound(int index) { return roundList.get(index); }

    public int getCurrentRound() {
        return currentRound;
    }

    public void add(RaceGroup raceGroup) {
        if(raceGroup == null) return;
        DiceGroup diceGroup = new DiceGroup(raceGroup.getDiceNum());
        raceGroupList.add(raceGroup);

        roundList.add(new GameRound(raceGroup,diceGroup));
        roundList.forEach(gameRound -> gameRound.registerStageListener(listener));
    }

    public void addAffector() {
        GameRound round = getRound(getCurrentRound());
        round.addAffector(new Visibler(getDiceGroup(currentRound),false), END);
        round.addAffector(new DiceAffector(getDiceGroup(currentRound), getRaceGroup(getCurrentRound()).getDiceNum()), START);
    }

    public void setHighlight(boolean value, boolean toEnemy) {
        raceGroupList.forEach(raceGroup -> {
            if(!raceGroup.isRounding() == toEnemy) {
                raceGroup.setHighlight(value);
            }
        });
    }

    public IntegerProperty getAutoLaunch() {
        return autoLaunch;
    }

    public void disposeAction(List<Action> actions) {
        if(actions == null) return;
        actions.forEach(action -> {
            if(action.getAction() == ActionMode.TO_ENEMY) {
                for(int i = 0 ; i < raceGroupList.size(); i++)
                    if(currentRound != i) {
                        System.out.println(currentRound);
                        System.out.println(i);
                        new Lighter(getRaceGroup(i), 1000).start();
                        roundList.get(currentRound).addAffector(new Lighter(getRaceGroup(i),false, 1000), GameRound.END);
                    }
            }

            if(action.getAction() == ActionMode.TO_PARTNER) {
                new Lighter(getRaceGroup(getCurrentRound()), 1000).start();
                roundList.get(currentRound).addAffector(new Lighter(getRaceGroup(getCurrentRound()), false, 1000), GameRound.END);
            }
        });
    }

    public void playFromStart() {
        currentRound = 0;
        raceGroupList.forEach(RaceGroup::reset);
        play();
    }

    public void play() {
        GameRound round = roundList.get(currentRound);
        round.setRoundState(true);
        round.run();
    }

    private GameRound nextRound() {
        currentRound = NextUtil.next(currentRound, 0, roundList.size() - 1);
        return roundList.get(currentRound);
    }

    public void nextStep() {
        GameRound round = roundList.get(currentRound);
        if(round.getCurrentStage() instanceof EndStage) {
            round.skip();
            round.setRoundState(false);
            round = nextRound();
            round.setRoundState(true);
            round.run();
        }else {
            round.nextStage();
        }
    }

    public ArrayList<Integer> rollDice() {
        return getDiceGroup(currentRound).roll();
    }

    public void registerListener(StageListener listener) {
        this.listener = listener;
    }
}
