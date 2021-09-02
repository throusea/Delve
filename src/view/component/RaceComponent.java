package view.component;

import com.sun.javafx.geom.Vec2d;
import controller.AnimationController;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import listener.RaceListener;
import model.animation.*;
import model.animation.effect.MagicCircle;
import model.dice.Dice;
import model.race.action.Action;
import model.race.Mark;
import model.race.Race;
import model.race.action.ActionMode;
import view.component.group.RaceGroupComponent;

import static util.BorderUtil.createBorder;

public class RaceComponent extends Pane  {

    private Label healthLabel, damageLabel;

    private Canvas canvas;

    DoubleProperty centX, centY;

    boolean selectDisable;

    BooleanProperty highlighted, isReadyAction, isDead;

    BooleanProperty pressResponse, dragResponse;

    RaceListener listener;

    StringProperty diceString;

    IntegerProperty health;

    IntegerProperty damage;

    public RaceComponent(int width, int height) {
        setPrefSize(width, height);
        setManaged(false);
        setBorder(createBorder(Color.BLACK));

        canvas = new Canvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0,0,width, height);
        healthLabel = new Label("HP");
        healthLabel.setLayoutX(0);
        healthLabel.setLayoutY(height);
        damageLabel = new Label("DP");
        damageLabel.setLayoutX(width);
        damageLabel.setLayoutY(height);
        this.getChildren().add(canvas);
        this.getChildren().add(healthLabel);
        this.getChildren().add(damageLabel);

        centX = new SimpleDoubleProperty();
        centY = new SimpleDoubleProperty();
        centX.bind(layoutXProperty().add(width/2));
        centY.bind(layoutYProperty().add(height/2));

        selectDisable = true;
        pressResponse = new SimpleBooleanProperty(false);
        dragResponse = new SimpleBooleanProperty(false);

        diceString = new SimpleStringProperty();
        health = new SimpleIntegerProperty();
        health.addListener((observable, oldValue, newValue) -> {
            if(oldValue.intValue() > newValue.intValue()) {
                AnimationController.addGameTime(new Quake(RaceComponent.this, 200));
                //new Quake(RaceComponent.this, 200).start();
            }
            healthLabel.setText(String.valueOf(newValue.intValue()));
        });
        damage = new SimpleIntegerProperty();
        damage.addListener((observable, oldValue, newValue) -> damageLabel.setText(String.valueOf(newValue.intValue())));
        highlighted = new SimpleBooleanProperty();
        highlighted.addListener((observable, oldValue, newValue) -> {
            gc.clearRect(0,0,width,height);
            gc.setStroke(newValue ? Color.RED : Color.BLACK);
            gc.fillText(listener.toString(), 0, 30);
            gc.strokeRect(0,0,width, height);
            AnimationController.addGameTime(new FadeOut(RaceComponent.this, 200, true));
            //new FadeOut(RaceComponent.this, 200, true).start();
        });
        isReadyAction = new SimpleBooleanProperty();
        isReadyAction.addListener((observable, oldValue, newValue) -> {
            gc.clearRect(0,0,width,height);
            gc.setStroke(newValue ? Color.YELLOW: Color.BLACK);
            gc.fillText(listener.toString(), 0, 30);
            gc.strokeRect(0,0,width, height);
            AnimationController.addGameTime(new FadeOut(RaceComponent.this, 300, true));
            //new FadeOut(RaceComponent.this, 300, true).start();
        });
        isDead = new SimpleBooleanProperty();
        isDead.addListener((observable, oldValue, newValue) -> {
            gc.clearRect(0,0,width,height);
            gc.setStroke(newValue ? Color.GRAY: Color.BLACK);
            gc.fillText(listener.toString(), 0, 30);
            gc.strokeRect(0,0,width, height);
            selectDisable = false;
            setDisable(newValue);
        });
    }

    public void addDice(DiceComponent dice) {
        if(isReadyAction.get()) listener.setKeyDice(new Dice(dice.getDot()));
        else {
            if (diceString.get() == null) diceString.set(String.valueOf(dice.getDot()));
            else diceString.set(diceString.get().concat(String.valueOf(dice.getDot())));
        }
        AnimationController.addGameTime(new DiceDisappear(dice, 500, false));
        AnimationController.addGameTime(new Mover(dice, 500, new Vec2d(this.centX.get() - (double) dice.getSize()/2, this.centY.get() - (double) dice.getSize()/2)));
    }

    public Mark getKeyMark() {
        return listener.getKeyMark();
    }

    public boolean getPressResponse() { return pressResponse.get(); }

    public boolean getDragResponse() { return dragResponse.get(); }

    public void setPressResponse(boolean value) {
        pressResponse.set(value);
    }

    public void setDragResponse(boolean dragResponse) {
        this.dragResponse.set(dragResponse);
    }

    public double getCentX() {
        return centX.get();
    }

    public double getCentY() {
        return centY.get();
    }

    public int getHealth() {
        return health.get();
    }

    public int getDamage() {
        return damage.get();
    }

    public void setHealth(int health) {
        listener.setHealth(health);
    }

    public void setDamage(int damage) {
        this.damage.set(damage);
    }

    public boolean isHighlighted() { return highlighted.get(); }

    public boolean isReadyAction() { return isReadyAction.get(); }

    public void setIsReadyAction(boolean isReadyAction) {
        this.isReadyAction.set(isReadyAction);
    }

    public void setPos(Vec2d vec2d) {
        setPos(vec2d.x, vec2d.y);
    }

    public void setPos(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setIsDead(boolean value) {
        listener.setIsDead(value);
    }

    public void setSelectDisable(boolean value) { selectDisable = isDead.get() || value; }

    public void setHighlighted(boolean value) { highlighted.set(value); }

    public boolean SelectDisable() { return selectDisable; }

    public void action(RaceComponent raceCpt) {
        Action action = getRace().action;
        if(action != null) action.run(raceCpt.getRace());
    }

    public void action(RaceGroupComponent raceGroupCpt) {
        Action action = getRace().action;
        if(action != null) {
            SequentialTransition seq = new SequentialTransition();
            Timeline timeline =  new Explosion((Pane)getParent(), getCentX(), getCentY(), 1000).getTimeline();
            seq.getChildren().addAll(new MagicCircle((Pane) getParent(), getCentX(),getCentY()).getTransition(),timeline);
            timeline.setOnFinished(event -> action.run(raceGroupCpt.getRaceGroup()));
            seq.playFromStart();
        }
    }

    public Race getRace() {
        return listener.getRace();
    }

    public ActionMode getActionMode() {
        return listener.getActionMode();
    }

    public void registerListener(Race race) {
        this.listener = race;
        diceString.bindBidirectional(race.getDiceString());
        health.bindBidirectional(race.getHealthProperty());
        damage.bind(race.getDamageProperty());
        isDead.bind(race.getDead());
        canvas.getGraphicsContext2D().fillText(race.toString(), 0, 40);
    }

    public void unregisterListener(RaceListener listener) {
        this.listener = null;
    }
}
