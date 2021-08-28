package model.round;

import listener.StageListener;
import model.affector.Affector;
import model.dice.DiceGroup;
import model.race.RaceGroup;
import model.race.monster.MonsterGroup;
import util.NextUtil;

import java.util.ArrayList;
import java.util.List;

public class GameRound {

    public static final int START = 0, ROLL = 1, ATTACK = 2, END = 3;

    private List<GameStage> stageList = new ArrayList<>();

    private RaceGroup raceGroup;

    private DiceGroup diceGroup;

    private int currentStage;

    private boolean isAuto;

    public GameRound(RaceGroup raceGroup, DiceGroup diceGroup) {
        stageList.add(new StartStage());
        stageList.add(new RollStage());
        stageList.add(new AttackStage());
        stageList.add(new EndStage());
        this.raceGroup = raceGroup;
        this.diceGroup = diceGroup;
        if(raceGroup instanceof MonsterGroup) isAuto = true;
        else isAuto = false;
    }

    public DiceGroup getDiceGroup() { return diceGroup; }

    public boolean isAuto() { return isAuto; }

    public void addAffector(Affector affector, int stageIndex) {
        stageList.get(stageIndex).getStageAffectors().add(affector);
    }

    public void setRoundState(boolean value) {
        raceGroup.setRounding(value);
    }

    public void registerStageListener(StageListener listener) {
        stageList.forEach(gameStage -> gameStage.registerListener(listener));
    }

    //# 每次阶段转换（包括Round）都会经过该函数
    public void run() {
        //持续更新骰子数量
        raceGroup.resetDiceNum();
        stageList.get(currentStage).run();
    }

    //# 跳过一个回合
    public void skip() {
        currentStage = NextUtil.next(currentStage, 0, stageList.size() - 1);
    }

    //# nextStage实际上就是上述两方法的结合
    public void nextStage() {
        skip();
        run();
    }

    public GameStage getCurrentStage() {
        return stageList.get(currentStage);
    }
}
