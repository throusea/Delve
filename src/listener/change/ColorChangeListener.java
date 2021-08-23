package listener.change;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static util.BorderUtil.createBorder;

public class ColorChangeListener implements ChangeListener {

    Node node;

    Color oldColor, newColor;

    public ColorChangeListener(Node node, Color oldColor, Color newColor) {
        this.node = node;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        ((Label)node).setBorder(createBorder(!(Boolean) newValue ? oldColor : newColor));
    }
}
