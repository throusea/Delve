package util;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BorderUtil {

    public static Border createBorder(Color color) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
    }

    public static Border createBorder(Color color, int width) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.DOTTED, new CornerRadii(20), new BorderWidths(width)));
    }
}
