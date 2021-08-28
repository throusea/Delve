package listener.change;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static util.BorderUtil.createBorder;

public class ColorChangeListener implements ChangeListener<Boolean> {

    Node node;

    Color oldColor, newColor;

    public ColorChangeListener(Node node, Color oldColor, Color newColor) {
        this.node = node;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void changed(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        ((Label)node).setBorder(createBorder(!newValue ? oldColor : newColor));
    }
}
