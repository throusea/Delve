package model.round;

import listener.StageListener;
import model.GameStation;
import model.affector.Affector;
import model.affector.DiceAffector;
import model.affector.Visibler;
import model.dice.Dice;
import model.dice.DiceGroup;
import model.race.Group;
import model.race.Race;
import model.race.RaceGroup;
import util.NextUtil;

import java.util.ArrayList;
import java.util.List;

public class GameRound {

    public static final int START = 0, ROLL = 1, ATTACK = 2, END = 3;

    private List<GameStage> stageList = new ArrayList<>();

    private RaceGroup raceGroup;

    private DiceGroup diceGroup;

    private int currentStage;

    public GameRound(RaceGroup raceGroup, DiceGroup diceGroup) {
        stageList.add(new StartStage());
        stageList.add(new RollStage());
        stageList.add(new AttackStage());
        stageList.add(new EndStage());
        this.raceGroup = raceGroup;
        this.diceGroup = diceGroup;
    }

    public DiceGroup getDiceGroup() { return diceGroup; }

    public void addAffector(Affector affector, int stageIndex) {
        stageList.get(stageIndex).getStageAffectors().add(affector);
    }

    public void setRoundState(boolean value) {
        raceGroup.setRounding(value);
    }

    public void registerStageListener(StageListener listener) {
        stageList.forEach(gameStage -> gameStage.registerListener(listener));
    }

    public void run() {
        stageList.get(currentStage).run();
    }

    public void skip() {
        currentStage = NextUtil.next(currentStage, 0, stageList.size() - 1);
    }

    public void nextStage() {
        currentStage = NextUtil.next(currentStage, 0, stageList.size() - 1);
        if(currentStage == ROLL) {
            addAffector(new Visibler(diceGroup, false), END);
            //addAffector(new DiceAffector(diceGroup, raceGroup.getTotalHealth()), START);
        }
        System.out.println(currentStage);
        run();
    }

    public GameStage getCurrentStage() {
        return stageList.get(currentStage);
    }
}
