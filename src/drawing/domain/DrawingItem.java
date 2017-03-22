/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

import java.io.Serializable;
import java.util.Comparator;
import javafx.scene.shape.Box;

/**
 *
 * @author piete
 */
public abstract class DrawingItem implements Comparable<DrawingItem>, Serializable {

    public abstract Point getAnchor();

    public abstract double getWidth();

    public abstract double getHeight();
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compareTo(DrawingItem drawingitem) {
        if (this.getAnchor().getX() + this.getAnchor().getY() < drawingitem.getAnchor().getX() + drawingitem.getAnchor().getY()) {
            return -1;
        } else if (drawingitem.getAnchor().getX() + drawingitem.getAnchor().getY() < this.getAnchor().getX() + this.getAnchor().getY()) {
            return 1;
        }
        return 0;
    }

    public boolean overlaps(DrawingItem item) {
        Point point1 = item.getAnchor();
        Point point2 = new Point(item.getAnchor().getX(), (item.getAnchor().getY() + item.getHeight()));
        Point point3 = new Point((item.getAnchor().getX() + item.getWidth()), item.getAnchor().getY());;
        Point point4 = new Point((item.getAnchor().getX() + item.getWidth()), (item.getAnchor().getY() + item.getHeight()));;

        if (insideBoundingBox(point1) || insideBoundingBox(point2) || insideBoundingBox(point3) || insideBoundingBox(point4)) {
            return true;
        }
        return false;
    }

    public boolean insideBoundingBox(Point point) {
        if (point.getX() >= this.getAnchor().getX() && point.getX() <= (this.getAnchor().getX() + this.getWidth()) && point.getY() >= this.getAnchor().getY() && point.getY() <= (this.getAnchor().getY() + this.getHeight())) {
            return true;
        } else {
            return false;
        }
    }

    public abstract void paintUsing(IPaintable paintable);

}
