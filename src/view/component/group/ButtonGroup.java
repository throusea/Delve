package view.component.group;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.animation.Balloon;

import java.util.ArrayList;
import java.util.List;

import static controller.GameController.cardLayout;

public class ButtonGroup {

    List<Label> labelList;

    public ButtonGroup(Pane pane, String[] strings) {
        labelList = new ArrayList<>();
        for (String s : strings) labelList.add(new Label(s));
        for (Label label : labelList) {
            label.setLayoutX(450);
            label.setLayoutY(200 + 100 * labelList.indexOf(label));
            label.setFont(new Font("Arial", 45));
            label.setTextFill(Color.BLACK);

            label.setOnMouseEntered(event -> new Balloon(label, 100, 1.2).start());
            label.setOnMouseExited(event -> new Balloon(label, 100, 1).start());
            label.setOnMousePressed(event -> label.setTextFill(Color.GRAY));
            label.setOnMouseReleased(event -> label.setTextFill(Color.BLACK));
            pane.getChildren().add(label);
        }
    }

    public void setTextFill(Paint value) {
        labelList.forEach(label -> {
            label.setTextFill(value);
            label.setOnMousePressed(event -> label.setTextFill(Color.GRAY));
            label.setOnMouseReleased(event -> label.setTextFill(value));
        });
    }

    public void setFont(Font font) {
        labelList.forEach(label -> label.setFont(font));
    }

    public void addShowListener(String buttonName, String paneName) {
        Label label = getLabel(buttonName);
        label.setOnMouseClicked(event -> cardLayout.show(paneName));
    }

    public Label getLabel(String name) {
        for (Label label : labelList) {
            if(label.getText().equals(name)) return label;
        }
        return null;
    }
}
