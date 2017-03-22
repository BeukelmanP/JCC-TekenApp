/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

/**
 *
 * @author piete
 */
public class Polygon extends DrawingItem {

    public Polygon(Point[] vertices, double weight, double width, double height, Color color) {

        this.vertices = vertices;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.setColor(color);
        calculate();

    }
    private Point anchor;
    private final Point[] vertices;
    private double weight;
    private double width;
    private double height;

    public Point[] getVertices() {
        return vertices;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public Point getAnchor() {
        return vertices[0];
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String string = "Polygon: Weight: " + weight + " Vertices: ";
        for (Point p : vertices) {
            string = string + p.toString() + " , ";
        }
        return string;
    }

    @Override
    public void paintUsing(IPaintable paintable) {
        paintable.paint(this);
    }

    public void calculate() {
        double xmax = 0;
        double ymax = 0;
        double xmin = 100000;
        double ymin = 100000;
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getX() > xmax) {
                xmax = vertices[i].getX();
            }
            if (vertices[i].getY() > ymax) {
                ymax = vertices[i].getY();
            }
            if (vertices[i].getX() < xmin) {
                xmin = vertices[i].getX();
            }
            if (vertices[i].getY() < ymin) {
                ymin = vertices[i].getY();
            }
        }
        anchor = new Point(xmin, ymin);
        width = xmax - xmin;
        height = ymax - ymin;
    }
}
