package view.sceneShift;

import controller.GameController;
import javafx.scene.layout.Pane;
import view.component.group.ButtonGroup;

import static controller.GameController.*;

public class SelectPane extends Pane {

    ButtonGroup buttonGroup;

    public SelectPane(GameController controller) {
        setLayoutX(0);
        setLayoutY(0);
        setManaged(false);
        setPrefSize(1280,800);
        //String[] strings = new String[]{"冒险模式", "对战模式"};
        String[] strings = new String[]{"Adventure", "PVP"};
        buttonGroup = new ButtonGroup(this,strings);

        //buttonGroup.addShowListener("冒险模式", "Game");
        //buttonGroup.addShowListener("对战模式", "Game");
        buttonGroup.addShowListener("Adventure", "Game");
        buttonGroup.addShowListener("PVP", "Game");
        buttonGroup.getLabel("Adventure").setOnMouseClicked(event -> {
            cardLayout.show("Game");
            controller.currentGameMode = ADVENTURE;
            controller.clearStation();
            controller.initGame();
        });
        buttonGroup.getLabel("PVP").setOnMouseClicked(event -> {
            cardLayout.show("Game");
            controller.currentGameMode = PVP;
            controller.clearStation();
            controller.initGame();
        });

    }
}
