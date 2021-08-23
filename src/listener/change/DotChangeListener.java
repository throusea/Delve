package listener.change;

import controller.AnimationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.animation.DiceAppear;
import model.animation.DiceDisappear;
import view.component.DiceComponent;

public class DotChangeListener implements ChangeListener {

    private DiceComponent diceComponent;

    public DotChangeListener(DiceComponent diceComponent) {
        this.diceComponent = diceComponent;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        int dot = (Integer) newValue;
        diceComponent.setText(String.valueOf(dot));
        if(dot == -1) {
            AnimationController.addGameTime(new DiceAppear(diceComponent, 1000));
            //new DiceAppear(diceComponent, 1000).start();
        }
    }
}
