package view.sceneShift;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.animation.FadeIn;

import javax.xml.soap.Text;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Root;


public class GlassPane extends Pane {

    public GlassPane() {
        setLayoutX(0);
        setLayoutY(0);
        setOpacity(0);
        setDisable(true);
        setManaged(false);
        setPrefSize(1280,800);
        Rectangle rectangle = new Rectangle(1280,800);
        rectangle.setFill(Color.BLACK);
        getChildren().add(rectangle);
    }

    public void appearMenu(double newValue) {
        //new FadeIn(this,500,false).start();
        KeyValue keyValue = new KeyValue(opacityProperty(), newValue);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
    }

    public void shiftState() {
        if(isDisable()) {
            setDisable(false);
            appearMenu(0.3);
        }else {
            setDisable(true);
            appearMenu(0);
        }
    }
}
