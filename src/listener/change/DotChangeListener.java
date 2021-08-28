package listener.change;

import controller.AnimationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.animation.DiceAppear;
import view.component.DiceComponent;

public class DotChangeListener implements ChangeListener<Number> {

    private DiceComponent diceComponent;

    public DotChangeListener(DiceComponent diceComponent) {
        this.diceComponent = diceComponent;
    }

    @Override
    public void changed(ObservableValue observable, Number oldValue, Number newValue) {
        int dot = newValue.intValue();
        diceComponent.setText(String.valueOf(dot));
        if(dot == -1) {
            AnimationController.addGameTime(new DiceAppear(diceComponent, 1000));
            //new DiceAppear(diceComponent, 1000).start();
        }
    }
}
