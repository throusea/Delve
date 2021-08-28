package model.animation;

import javafx.scene.Node;

/*
 *
 */
public class Pause extends Animation{

    public Pause(Node node, double millis) {
        super(node, millis);
    }

    @Override
    protected Void call() {
        return null;
    }

    @Override
    public void start() {

    }
}
