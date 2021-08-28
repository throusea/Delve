package view.sceneShift;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import view.component.group.ButtonGroup;

import static controller.GameController.cardLayout;

public class MenuPane extends Pane {

    ButtonGroup buttonGroup;

    public MenuPane() {
        setLayoutX(0);
        setLayoutY(0);
        setManaged(false);
        setPrefSize(1280,800);
        String[] strings = new String[]{"New Game", "Continue", "Set", "Exit"};
        buttonGroup = new ButtonGroup(this, strings);
        buttonGroup.addShowListener("New Game", "Game");
        buttonGroup.addShowListener("Continue", "");
        buttonGroup.addShowListener("Set", "Set");
        buttonGroup.getLabel("Exit").setOnMouseClicked(event -> System.exit(0));
    }

    public void addMouseListener(Label label) {
        label.setOnMouseClicked(event -> {
            switch (label.getText()) {
                case "New Game": {
                    cardLayout.show("Game");
                    break;
                }
                case "Continue": {
                    cardLayout.show("");
                    break;
                }
                case "Set": {
                    cardLayout.show("Set");
                    break;
                }
                case "Exit": {
                    break;
                }
            }
        });
    }
}
