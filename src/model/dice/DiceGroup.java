package model.dice;

import java.util.ArrayList;
import java.util.List;

public class DiceGroup {

    List<Dice> diceList;

    int diceCount;

    public DiceGroup(int diceNum) {
        diceList = new ArrayList<>();
        while(diceNum-- > 0) {
            diceList.add(new Dice());
        }
        diceCount = 3;
    }

    public ArrayList<Integer> roll() {
        if(diceCount == 0) return null;
        ArrayList<Integer> dotList = new ArrayList<>();
        diceList.forEach(dice -> {
            dice.roll();
            dotList.add(dice.getDot());
        });
        diceCount--;
        return dotList;
    }

    public int getDiceNum() {
        return diceList.size();
    }

    public void addDice() {
        diceList.add(new Dice());
    }

    public void addDice(int dot) {
        diceList.add(new Dice(dot));
    }

    public void removeDice() {
        Dice dice = diceList.remove(0);
        diceList.forEach(System.out::println);
        dice.diceCpt.setDisable(true);
    }

    public void reset() {
        diceCount = 3;
        clearDice();
    }

    public void reset(int count) {
        diceCount = count;
        clearDice();
    }

    public void clearDice() {
        diceList.forEach(Dice::clear);
    }

    public List<Dice> getList(){ return diceList; }

    public String toString() {
        StringBuffer s = new StringBuffer();
        diceList.forEach(dice -> s.append(dice.getDot()));
        return s.toString();
    }
}
