package view.component.group;

import com.sun.javafx.geom.Vec2d;
import controller.AnimationController;
import javafx.scene.layout.Pane;
import listener.change.RaceGroupListener;
import model.animation.FadeIn;
import model.race.Group;
import model.race.RaceGroup;
import view.component.Bound;
import view.component.RaceComponent;
import view.component.RaceStaticComponent;

import java.util.ArrayList;
import java.util.List;

public class RaceGroupComponent extends GroupComponent {

    private List<RaceComponent> raceComponentList;

    private List<RaceStaticComponent> raceStaticComponentList;

    private Group group;

    private RaceGroupListener groupListener;

    public RaceGroupComponent(Bound bound, Group group) {
        super(bound);
        raceComponentList = new ArrayList<>();
        raceStaticComponentList = new ArrayList<>();
        this.group = group;
    }

    public void add(RaceComponent raceComponent, Pane pane) {
        raceComponentList.add(raceComponent);
        setRandPos(raceComponent);
        raceComponent.setSelectDisabled(!isRounding());
        pane.getChildren().add(raceComponent);
        AnimationController.addGameTime(new FadeIn(raceComponent, 500,false));
        //new FadeIn(raceComponent,500).start();
    }

    public void add(RaceStaticComponent raceStaticComponent, Pane pane) {
        raceStaticComponentList.add(raceStaticComponent);
        pane.getChildren().add(raceStaticComponent);
    }

    public void remove(RaceComponent raceComponent, Pane pane) {
        raceComponentList.remove(raceComponent);
    }

    public List<RaceComponent> getRaceComponentList() {
        return raceComponentList;
    }

    public Group getGroup() { return group; }

    public void setStaticLocation(double x, double y) {
        final double[] value = {0};
        raceStaticComponentList.forEach(raceCpt -> {
            raceCpt.setLoc(x + value[0], y);
            value[0] += 170;
        });
    }

    public void setStaticLocation(Vec2d vec2d) {
        setStaticLocation(vec2d.x, vec2d.y);
    }

    private void setRandPos(RaceComponent raceComponent) {
        Vec2d vector;
        do {
            vector = bound.getRandPoint(true);
        }while(!bound.contains(vector.x, vector.y, vector.x + 80, vector.y + 60));
        raceComponent.setPos(vector);
        bound.addSelectedRange(vector.x, vector.y, 80, 60);
    }

    public void registerListener(RaceGroupListener listener) { this.groupListener = listener; }

    public boolean isRounding() {
        return groupListener.isRounding();
    }

    public RaceGroup getRaceGroup() {
        return groupListener.getRaceGroup();
    }
}
