package view.sceneShift;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.animation.Animation;
import model.animation.FadeIn;
import model.animation.FadeOut;
import model.animation.Mover;
import model.round.GameStage;
import org.w3c.dom.css.Rect;

import javax.xml.datatype.Duration;

public class StageTabPane extends Pane {

    private static Label stageLb;

    private static Rectangle rectangle;

    private static Rectangle background;

    public StageTabPane() {
        setLayoutX(0);
        setLayoutY(0);
        setManaged(false);
        setBackground(null);
        setPrefSize(1280,800);
        stageLb = new Label();
        stageLb.setLayoutY(270);
        stageLb.setPrefSize(480,100);
        stageLb.setFont(new Font("Arial", 85));
        stageLb.setTextFill(Color.BLACK);
        rectangle = new Rectangle(380,10);
        rectangle.setLayoutX(-380);
        rectangle.setLayoutY(365);
        rectangle.setFill(Color.BLACK);
        background = new Rectangle(1280, 800);
        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setFill(Color.color(0.8,0.8,0.8));
        background.setOpacity(0);
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        getChildren().addAll(background,stageLb,rectangle);
        setDisable(true);
    }

    public static void shift(GameStage stage) {
        stageLb.setText(stage.toString());
        stageLb.setLayoutX(1280);
        SequentialTransition seqTrans1 = new SequentialTransition();
        seqTrans1.getChildren().addAll(
                new Mover(stageLb,400,new Vec2d(620,270)).getTimeline(),
                new Mover(stageLb, 1200,new Vec2d(500,270)).getTimeline(),
                new Mover(stageLb, 400,new Vec2d(-500,270)).getTimeline()
        );

        rectangle.setLayoutX(-380);
        SequentialTransition seqTrans2 = new SequentialTransition();
        seqTrans2.getChildren().addAll(
                new Mover(rectangle,400,new Vec2d(400,365)).getTimeline(),
                new Mover(rectangle, 1200,new Vec2d(520,365)).getTimeline(),
                new Mover(rectangle, 400,new Vec2d(1280,365)).getTimeline()
        );
        SequentialTransition seqTrans3 = new SequentialTransition();
        seqTrans3.getChildren().addAll(
                new FadeIn(background, 400, false).getTimeline(),
                new Mover(background, 1200, new Vec2d(0,0)).getTimeline(),
                new FadeOut(background, 400,false).getTimeline()
        );
        seqTrans1.playFromStart();
        seqTrans2.playFromStart();
        seqTrans3.playFromStart();
    }
}
