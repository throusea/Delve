package view.sceneShift;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.animation.FadeIn;
import model.animation.FadeOut;
import view.component.group.ButtonGroup;

import static controller.GameController.cardLayout;


public class GlassPane extends Pane {

    ButtonGroup buttonGroup;

    Rectangle rectangle;
    public GlassPane() {
        setLayoutX(0);
        setLayoutY(0);
        setOpacity(0);
        setDisable(true);
        setManaged(false);
        setPrefSize(1280,800);
        rectangle = new Rectangle(1280,800);
        rectangle.setFill(Color.BLACK);
        getChildren().add(rectangle);

        String[] s = {"Resume", "Music On", "Back To Menu"};
        buttonGroup = new ButtonGroup(this, s);
        buttonGroup.setTextFill(Color.WHITE);
        buttonGroup.getLabel("Resume").setOnMouseClicked(event -> show());
        buttonGroup.getLabel("Back To Menu").setOnMouseClicked(event -> {
            show();
            cardLayout.show("Menu");
        });
    }

    public void show() {
        if(isDisable()) {
            setDisable(false);
            new FadeIn(this, 300, false).startTo(0.3);
        }else {
            setDisable(true);
            new FadeOut(this, 300, false).startFrom(0.3);
        }
    }
}
