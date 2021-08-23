package model.race;

import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Bounds;
import view.component.Bound;

public class Location {

    private int index;
    private Bounds bounds;

    public Location(int index, Bounds bounds) {
        this.index = index;
        this.bounds = bounds;
    }

    public Bound getBound() {
        switch (index) {
            case 0: return new Bound(0, 0, bounds.getWidth() / 2, bounds.getHeight() / 2);
            case 1: return new Bound(bounds.getWidth()/2, bounds.getHeight()/2, bounds.getWidth()/2, bounds.getHeight()/2);
            case 2: return new Bound(0,bounds.getHeight() / 2, bounds.getWidth()/2, bounds.getHeight() / 2);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public Vec2d getStaticLocation() {
        switch (index) {
            case 0: return new Vec2d(0,0);
            case 1: return new Vec2d(bounds.getWidth() - 680, bounds.getHeight() - 65);
        }
        return null;
    }

    public Vec2d getModifiedLocation() {
        switch (index) {
            case 0: return new Vec2d(2,300);
            case 1: return new Vec2d(2,500);
        }
        return null;
    }
}
