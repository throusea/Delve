package view;

import com.sun.javafx.geom.Vec2d;
import controller.AnimationController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import listener.PanelListener;
import model.animation.Mover;
import model.dice.Dice;
import model.race.*;
import view.component.*;
import view.component.group.DiceGroupComponent;
import view.component.group.RaceGroupComponent;

import java.util.ArrayList;
import java.util.List;

public class GamePanel implements PanelListener {

    public static final int NORMAL = 1, CHILD = 2, WAIT = 3, FORBID = 4;

    ObservableList<Node> nodeList;

    Pane mainPane;

    DiceGroupComponent dices;

    List<RaceGroupComponent> raceGroupCpts;

    private double x, y, initX, initY;

    private boolean isDragged;

    private Node node;

    public GamePanel(Pane mainPane) {
        this.mainPane = mainPane;
        nodeList = mainPane.getChildren();
        raceGroupCpts = new ArrayList<>();

        mainPane.setOnMouseDragged(event -> {

            if(node instanceof DiceComponent && isDragged) {
                DiceComponent dice = (DiceComponent) node;
                dice.setPos(dice.getLayoutX() + event.getX() - x, dice.getLayoutY() + event.getY() - y);
            }

            if(node instanceof RaceComponent && isDragged) {
                RaceComponent race = (RaceComponent) node;
                race.setPos(race.getLayoutX() + event.getX() - x, race.getLayoutY() + event.getY() - y);
            }

            setNodePos(event.getX(), event.getY());
        });

        mainPane.setOnMousePressed(event -> {
            setNodePos(event.getX(), event.getY());

            node = event.getPickResult().getIntersectedNode();

            switch (getNodeAns()) {
                case CHILD: {
                    node = node.getParent();
                } case NORMAL: {
                    isDragged = true;
                    initX = node.getLayoutX();
                    initY = node.getLayoutY();
                    break;
                }
                default:
            }
        });

        mainPane.setOnMouseReleased(event -> {

            boolean eventAns = handleEvent();
            if(eventAns && !(node instanceof DiceComponent)) {
                AnimationController.addGameTime(new Mover(node, 500, new Vec2d(initX, initY)));
                //new Mover(node, 500, new Vec2d(initX, initY)).start();
            }
            if(!eventAns && isDragged && !legalPosition()) {
                AnimationController.addGameTime(new Mover(node, 500, new Vec2d(initX, initY)));
                //new Mover(node, 500, new Vec2d(initX, initY)).start();
            }
            isDragged = false;
        });
    }

    public void setNodePos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addDiceGroupCpt(DiceGroupComponent dices) {
        this.dices = dices;
    }

    public void add(RaceGroupComponent raceGroupCpt) {
        raceGroupCpts.add(raceGroupCpt);
    }

    public RaceGroupComponent getCurrentRoundRaceGroup() {
        for (RaceGroupComponent raceGroupCpt : raceGroupCpts) {
            if(raceGroupCpt.isRounding()) return raceGroupCpt;
        }
        return null;
    }

    public RaceGroupComponent getRaceGroupCpt(RaceGroup raceGroup) {
        for (RaceGroupComponent raceGroupCpt : raceGroupCpts) {
            if(raceGroupCpt.isListener(raceGroup)) return raceGroupCpt;
        }
        return null;
    }

    public RaceGroupComponent getRaceGroupCpt(int index) {
        return raceGroupCpts.get(index);
    }

    public int getNodeAns() {
        if(node instanceof DiceComponent) return NORMAL;
        if(node instanceof RaceComponent) return ((RaceComponent) node).isSelectDisabled() ? WAIT : NORMAL;
        while(node instanceof Text || node instanceof Canvas || node instanceof Label) node = node.getParent();
        if(node instanceof DiceComponent) return NORMAL;
        if(node instanceof RaceComponent) return ((RaceComponent) node).isSelectDisabled() ? WAIT : NORMAL;
        return FORBID;
    }

    public boolean handleEvent() {
        if(node instanceof DiceComponent) {
            CharacterComponent chr = (CharacterComponent) detectRace(true, null);
            if(chr != null && !chr.isSelectDisabled()) {
                chr.addDice((DiceComponent) node);
                return true;
            }
        }

        if(node instanceof CharacterComponent) {
            RaceComponent subject = (RaceComponent) node;
            RaceComponent object = detectRace(false, subject);

            if(subject.getPressResponse() && Vec2d.distance(initX,initY,node.getLayoutX(),node.getLayoutY()) <= 3) {
                System.out.println("OK");
                if(subject.getDragResponse()) {
                    new ActionMode(subject, subject, subject.getActionMode()).run();
                }else {
                    raceGroupCpts.forEach(raceGroupCpt -> {
                        if (raceGroupCpt != getCurrentRoundRaceGroup() && subject.getActionMode() == ActionMode.TO_ENEMY)
                            new ActionMode(subject, raceGroupCpt.getRaceComponentList(), subject.getActionMode()).run();
                        if (raceGroupCpt == getCurrentRoundRaceGroup() && subject.getActionMode() == ActionMode.TO_PARTNER)
                            new ActionMode(subject, raceGroupCpt.getRaceComponentList(), subject.getActionMode()).run();
                    });
                }
            }else if(object != null && subject.getDragResponse() && isDragged && object.isHighlighted()) {
                new ActionMode(subject, object, subject.getActionMode()).run();
                return true;
            }
        }
        return false;
    }

    public RaceComponent detectRace(boolean isCurrentRoundRaceGroup, RaceComponent keyRace) {
        return isCurrentRoundRaceGroup ? findCloseRace(getCurrentRoundRaceGroup(), keyRace) : findCloseRace(keyRace);
    }

    public boolean legalPosition() {
        if(node instanceof DiceComponent)
            return dices.getBound().contains(x, y, x + dices.getSize(), y + dices.getSize());
        if(node instanceof RaceComponent)
            return getCurrentRoundRaceGroup().getBound().contains(x, y, x + 80, y + 60);
        return false;
    }

    public RaceComponent findCloseRace(RaceGroupComponent characters, RaceComponent keyRace) {
        double minDis = 99999999;
        RaceComponent chr0 = null;
        for (RaceComponent chr : characters.getRaceComponentList()) {
            if(chr.equals(keyRace)) continue;
            if (minDis > Vec2d.distance(x, y, chr.getCentX(), chr.getCentY())) {
                minDis = Math.min(minDis, Vec2d.distance(x, y, chr.getCentX(), chr.getCentY()));
                chr0 = chr;
            }
        }
        return minDis < 100 ? chr0 : null;
    }

    public RaceComponent findCloseRace(RaceComponent keyRace) {
        double minDis = 99999999;
        RaceComponent race0 = null;
        for (RaceGroupComponent raceGroupCpt: raceGroupCpts) {
            RaceComponent raceCpt = findCloseRace(raceGroupCpt, keyRace);
            if(raceCpt != null && minDis > Vec2d.distance(x,y,raceCpt.getCentX(), raceCpt.getCentY())){
                minDis = Math.min(minDis, Vec2d.distance(x,y,raceCpt.getCentX(), raceCpt.getCentY()));
                race0 = raceCpt;
            }
        }
        return minDis < 100 ? race0 : null;
    }

    @Override
    public void moveDice(Dice dice, Race race) {

    }
}
