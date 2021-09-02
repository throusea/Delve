package view;

import com.sun.javafx.geom.Vec2d;
import controller.AnimationController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import listener.AutoListener;
import listener.PanelListener;
import model.animation.Mover;
import model.dice.Dice;
import model.race.*;
import model.race.action.ActionMode;
import view.component.*;
import view.component.group.DiceGroupComponent;
import view.component.group.RaceGroupComponent;

import java.util.ArrayList;
import java.util.List;

public class GamePanel implements PanelListener {

    //public static final int NORMAL = 1, CHILD = 2, WAIT = 3, FORBID = 4;

    ObservableList<Node> nodeList;

    Pane mainPane;

    AutoListener autoListener;

    List<DiceGroupComponent> diceGroupCpts;

    List<RaceGroupComponent> raceGroupCpts;

    private double x, y, initX, initY;

    private boolean lockIn;

    private Node node;

    public GamePanel(Pane mainPane) {
        this.mainPane = mainPane;
        nodeList = mainPane.getChildren();
        raceGroupCpts = new ArrayList<>();
        diceGroupCpts = new ArrayList<>();

        mainPane.setOnMouseDragged(event -> {

            if(node instanceof DiceComponent && lockIn) {
                DiceComponent dice = (DiceComponent) node;
                dice.setPos(dice.getLayoutX() + event.getX() - x, dice.getLayoutY() + event.getY() - y);
            }

            if(node instanceof RaceComponent && lockIn) {
                RaceComponent race = (RaceComponent) node;
                race.setPos(race.getLayoutX() + event.getX() - x, race.getLayoutY() + event.getY() - y);
            }

            setNodePos(event.getX(), event.getY());
        });

        mainPane.setOnMousePressed(event -> {
            setNodePos(event.getX(), event.getY());

            node = event.getPickResult().getIntersectedNode();

            if(findFatherNode()) lockIn = true;
            initX = node.getLayoutX();
            initY = node.getLayoutY();
        });

        mainPane.setOnMouseReleased(event -> {

            boolean eventAns = handleEvent();
            if(eventAns && !(node instanceof DiceComponent)) {
                AnimationController.addGameTime(new Mover(node, 500, new Vec2d(initX, initY)));
                //new Mover(node, 500, new Vec2d(initX, initY)).start();
            }
            if(!eventAns && lockIn && !legalPosition()) {
                AnimationController.addGameTime(new Mover(node, 500, new Vec2d(initX, initY)));
                //new Mover(node, 500, new Vec2d(initX, initY)).start();
            }
            lockIn = false;
        });
    }

    public void setNodePos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addDiceGroupCpt(DiceGroupComponent dices) {
        diceGroupCpts.add(dices);
    }

    public void add(RaceGroupComponent raceGroupCpt) {
        raceGroupCpts.add(raceGroupCpt);
    }

    public void clear() {
        diceGroupCpts.clear();
        raceGroupCpts.clear();
    }

    public RaceGroupComponent getCurrentRoundRaceGroup() {
        for (RaceGroupComponent raceGroupCpt : raceGroupCpts) {
            if(raceGroupCpt.isRounding()) return raceGroupCpt;
        }
        return null;
    }

    public boolean findFatherNode() {
        if(node instanceof DiceComponent) return true;
        while(node instanceof Text || node instanceof Canvas || node instanceof Label) {
            node = node.getParent();
            if(node instanceof DiceComponent) break;
        }
        if(node instanceof RaceComponent) return !((RaceComponent) node).SelectDisable();
        return node instanceof DiceComponent;
    }

    public boolean handleEvent() {
        if(node instanceof DiceComponent) {
            CharacterComponent chr = (CharacterComponent) detectRace(true, null);
            if(chr != null && !chr.SelectDisable()) {
                chr.addDice((DiceComponent) node);
                return true;
            }
        }

        if(node instanceof CharacterComponent) {
            RaceComponent subject = (RaceComponent) node;
            RaceComponent object = detectRace(false, subject);
            System.out.println(subject);
            System.out.println(object);

            System.out.printf("init %.1f %.1f\n", initX, initY);
            System.out.printf("node %.1f %.1f\n", node.getLayoutX(), node.getLayoutY());
            System.out.println(Vec2d.distance(initX,initY,node.getLayoutX(),node.getLayoutY()) <= 3);
            if(Vec2d.distance(initX,initY,node.getLayoutX(),node.getLayoutY()) <= 3) {
                monAttack(subject);
            }
            if(subject.getPressResponse() && Vec2d.distance(initX,initY,node.getLayoutX(),node.getLayoutY()) <= 3) {
                if(subject.getDragResponse()) {
                    subject.action(subject);
                }
                else raceGroupCpts.forEach(raceGroupCpt -> {
                        if (raceGroupCpt != getCurrentRoundRaceGroup() && subject.getActionMode() == ActionMode.TO_ENEMY)
                            subject.action(raceGroupCpt);
                        if (raceGroupCpt == getCurrentRoundRaceGroup() && subject.getActionMode() == ActionMode.TO_PARTNER)
                            subject.action(raceGroupCpt);
                });
            }else if(object != null && object.isHighlighted() && subject.getDragResponse() && lockIn) {
                subject.action(object);
                return true;
            }
        }
        return false;
    }

    public void monAttack(RaceComponent object) {
        autoListener.attack(object);
    }

    public RaceComponent detectRace(boolean isCurrentRoundRaceGroup, RaceComponent keyRace) {
        return isCurrentRoundRaceGroup ? findCloseRace(getCurrentRoundRaceGroup(), keyRace) : findCloseRace(keyRace);
    }

    public boolean legalPosition() {
        if(node instanceof DiceComponent)
            return diceGroupCpts.get(0).getBound().contains(x, y, x + diceGroupCpts.get(0).getSize(), y + diceGroupCpts.get(0).getSize());
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

    public void registerAutoListener(AutoListener autoListener) {
        this.autoListener = autoListener;
    }
}
