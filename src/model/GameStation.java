package model;

import listener.StageListener;
import model.affector.Affector;
import model.affector.Lighter;
import model.dice.DiceGroup;
import model.race.*;
import model.round.*;
import util.NextUtil;

import java.util.ArrayList;
import java.util.List;

public class GameStation {

    List<GameRound> roundList;

    List<Affector> affectorList;

    List<RaceGroup> raceGroupList;

    GameEnvironment environment;

    StageListener listener;

    int currentRound;

    public GameStation() {
        roundList = new ArrayList<>();
        affectorList = new ArrayList<>();
        raceGroupList = new ArrayList<>();
    }

    public DiceGroup getDiceGroup(int index) { return roundList.get(index).getDiceGroup(); }

    public RaceGroup getRaceGroup(int index) {
        return raceGroupList.get(index);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void add(RaceGroup raceGroup) {
        if(raceGroup == null) return;
        raceGroupList.add(raceGroup);

        roundList.add(new GameRound(raceGroup, new DiceGroup(raceGroup.getDiceNum())));
        roundList.forEach(gameRound -> gameRound.registerStageListener(listener));
    }

    public void setHighlight(boolean value, boolean toEnemy) {
        raceGroupList.forEach(raceGroup -> {
            if(!((!raceGroup.isRounding()) ^ (toEnemy))) {
                raceGroup.setHighlight(value);
            }
        });
    }

    public void disposeAction(List<Race> races) {
        races.forEach(race -> {
            if(race.getBasicActionMode() == ActionMode.TO_ENEMY) {
                for(int i = 0 ; i < raceGroupList.size(); i++)
                    if(currentRound != i) {
                        System.out.println(currentRound);
                        System.out.println(i);
                        new Lighter(getRaceGroup(i), 1000).start();
                        //affectorList.add(new Lighter(getRaceGroup(i), 1000));
                        roundList.get(currentRound).addAffector(new Lighter(getRaceGroup(i),false, 1000), GameRound.END);
                    }
            }

            if(race.getBasicActionMode() == ActionMode.TO_PARTNER) {
                new Lighter(getRaceGroup(getCurrentRound()), 1000).start();
                //affectorList.add(new Lighter(getRaceGroup(getCurrentRound()), 1000));
                roundList.get(currentRound).addAffector(new Lighter(getRaceGroup(getCurrentRound()), false, 1000), GameRound.END);
            }
        });
    }

    public void playFromStart() {
        currentRound = 0;
        raceGroupList.forEach(raceGroup -> raceGroup.reset());
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
