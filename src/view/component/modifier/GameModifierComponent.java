package view.component.modifier;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import listener.StageListener;
import model.affector.SkillGainer;
import model.race.Race;
import model.race.RaceGroup;
import util.NextUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameModifierComponent extends Pane {

    StageListener stageListener;

    Label chrLabel, dagLabel;

    TextField textField;

    int raceIndex;

    public GameModifierComponent(RaceGroup raceGroup, int posX, int posY) {
        setManaged(false);
        setPrefSize(120,60);

        setLayoutX(posX);
        setLayoutY(posY);
        raceIndex = -1;
        chrLabel = new Label();
        dagLabel = new Label();
        textField = new TextField();
        chrLabel.setLayoutX(0);
        chrLabel.setLayoutY(0);
        chrLabel.setPrefSize(120,20);
        chrLabel.setText("Rand Character");
        chrLabel.setOnMouseClicked(event -> {
            raceIndex = NextUtil.next(raceIndex,0,raceGroup.getSize() - 1);
            chrLabel.setText(raceGroup.get(raceIndex).toString());
        });
        dagLabel.setLayoutX(0);
        dagLabel.setLayoutY(30);
        dagLabel.setText("Dam");
        dagLabel.setPrefSize(50,20);
        textField.setLayoutX(40);
        textField.setLayoutY(30);
        textField.setPrefSize(70,20);
        textField.setOnAction(event -> {
            String s = textField.getText();
            if(s != null && isNumeric(s)) {
                Race race = raceGroup.get(chrLabel.getText());
                System.out.println(race);
                new SkillGainer(race, race.getActionMode(), Integer.parseInt(s)).start();
                stageListener.actionHandle();
            }
            textField.deleteText(0, textField.getLength());
        });

        getChildren().addAll(chrLabel, dagLabel, textField);
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ) {
            return false;
        }
        return true;
    }

    public void registerListener(StageListener listener) {
        this.stageListener = listener;
    }
}
