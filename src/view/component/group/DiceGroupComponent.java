package view.component.group;

import javafx.scene.layout.Pane;
import model.dice.Dice;
import model.dice.DiceGroup;
import view.component.Bound;
import view.component.DiceComponent;

import java.util.ArrayList;
import java.util.List;

public class DiceGroupComponent extends GroupComponent {

    List<DiceComponent> diceComponentList;

    double size = 50;

    public DiceGroupComponent(DiceGroup diceGroup, Bound bound) {
        super(bound);
        diceComponentList = new ArrayList<>();

        List<Dice> dices = diceGroup.getList();
        dices.forEach(dice -> {
            DiceComponent diceCpt = new DiceComponent();
            dice.setDiceCpt(diceCpt);
            diceCpt.registerListener(dice);
            diceCpt.setVisible(false);
            diceCpt.setPos(bound.getRandPoint(size, size));
            diceComponentList.add(diceCpt);
        });
    }

    public void setRandPos() {
        diceComponentList.forEach(diceComponent -> {
            diceComponent.setVisible(true);
            diceComponent.setPos(bound.getRandPoint(size, size));
        });
    }

    public Bound getBound() {
        return bound;
    }

    public double getSize() {
        return size;
    }

    public void add(Dice dice, Pane pane) {
        DiceComponent diceC = new DiceComponent();
        diceC.registerListener(dice);
        pane.getChildren().add(diceC);
    }

    public List<DiceComponent> getList() { return diceComponentList; }
}
