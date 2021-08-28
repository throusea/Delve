package view.sceneShift;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class CardLayout {

    public Map<String, Pane> map;

    public Pane currentPane;

    public CardLayout() {
        map = new HashMap<>();
    }

    public boolean add(Pane pane,  String s) {
        if(map.get(s) != null) return false;
        pane.setVisible(false);
        pane.setDisable(true);
        map.put(s, pane);
        return true;
    }

    public boolean remove(String s) {
        if(map.get(s) == null) return false;
        map.remove(s);
        return true;
    }

    public void show(String s) {
        Pane pane = map.get(s);
        if(pane != null) {
            pane.setVisible(true);
            pane.setDisable(false);
            if(currentPane != null) {
                currentPane.setVisible(false);
                currentPane.setDisable(true);
            }
            currentPane = pane;
        }
    }
}
