package view.component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.race.Race;
import model.race.character.Wizard;

public class RaceStaticComponent extends Pane {

    Label label;
    Button button;

    ProgressBar healthBar;

    HBox box;

    public RaceStaticComponent(Race race) {
        setPrefSize(160,40);
        setManaged(false);
        label = new Label(race.toString());
        label.setLayoutX(0);
        label.setLayoutY(0);
        button = new Button(race.getCurrentMode());
        button.setOnMouseClicked(event -> {
            race.shiftActionMode();
            button.setText(race.getCurrentMode());
        });
        button.setManaged(true);
        button.setLayoutX(60);
        button.setLayoutY(0);
        button.setPrefSize(100,20);
        healthBar = new ProgressBar();
        healthBar.setLayoutX(0);
        healthBar.setLayoutY(25);
        healthBar.setPrefSize(160, 20);
        healthBar.setProgress(1);
        box = new HBox();
        box.setLayoutX(0);
        box.setLayoutY(50);
        box.setSpacing(5);
        box.setPrefSize(160,25);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //撤销操作，暂时不写
            }
        });
        race.getHealth().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                healthBar.setProgress(newValue.doubleValue() / race.getMaxHealth().get());
            }
        });
        race.getDiceString().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null) {
                    Label label = new Label(String.valueOf(newValue.charAt(newValue.length()-1)));
                    label.setPrefSize(25,25);
                    label.setBorder(null);
                    box.getChildren().add(label);
                }else {
                    box.getChildren().clear();
                }
            }
        });
        getChildren().addAll(label, box, button, healthBar);
    }

    public void setLoc(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }
}
