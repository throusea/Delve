package util;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BorderUtil {

    public static Border createBorder(Color color) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
    }
}
