package view.component;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;
import java.util.List;

import static util.RandomUtil.nextDouble;

public class Bound {

    double posX, posY, width, height;

    List<Bound> selectedRanges;

    public Bound(double posX, double posY, double width, double height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        selectedRanges = new ArrayList<>();
    }

    public boolean contains(double x1,double y1, double x2, double y2) {
        return posX < x1 && x2 < posX + width && posY < y1 && y2 < posY + height;
    }

    public boolean contains(double x1, double y1) {
        return contains(x1, y1, x1, y1);
    }

    public boolean contains(Bound bound) {
        return contains(bound.posX, bound.posY, bound.width, bound.height);
    }

    public boolean intersect(Bound bound) {
        return contains(bound.posX, bound.posY) ||
                contains(bound.posX, bound.posY + height) ||
                contains(bound.posX + width, bound.posY) ||
                contains(bound.posX + width, bound.posY + height);
    }

    public void setLocation(double x, double y) {
        posX = x;
        posY = y;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void addSelectedRange(double x, double y, double w, double h) {
        selectedRanges.add(new Bound(x,y,w,h));
    }

    public Vec2d getRandPoint() {
        return new Vec2d(nextDouble(posX, posX + width), nextDouble(posY, posY + height));
    }

    public Vec2d getRandPoint(double w, double h) {
        return new Vec2d(nextDouble(posX, posX + width - w), nextDouble(posY, posY + height - h));
    }

    public Vec2d getRandPoint(boolean isAvoidSelectedRange) {
        Vec2d vec2d = new Vec2d(nextDouble(posX, posX + width), nextDouble(posY, posY + height));
        if(!isAvoidSelectedRange) return vec2d;
        else {
            boolean value = false;
            while(!value) {
                vec2d = new Vec2d(nextDouble(posX, posX + width), nextDouble(posY, posY + height));
                value = true;
                for (Bound bound : selectedRanges) if(bound.intersect(new Bound(vec2d.x, vec2d.y, vec2d.x+80, vec2d.y+60))) value = false;
            }
        }
        return vec2d;
    }
}
