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
public class Oval extends DrawingItem {
    
    private Point anchor;
    private double width;
    private double height;
    private double weight;

    /**
     * This is a simulation of Prof. Knuth's MIX computer.
     *
     * @param anchor
     * @param width
     * @param height
     * @param weight
     */
    public Oval(Point anchor, double width, double height, double weight, Color color) {
        this.anchor = anchor;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.setColor(color);
    }

    /**
     * Gets the Anchor
     *
     * @return anchor
     *
     */
    @Override
    public Point getAnchor() {
        return anchor;
    }

    /**
     * Sets the Anchor
     *
     * @param anchor
     *
     */
    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }

    /**
     * Gets the Width
     *
     * @return width
     *
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width
     *
     * @param width
     *
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the Height
     *
     * @return height
     *
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * Sets the Height
     *
     * @param height
     *
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the Weight
     *
     * @return weight
     *
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the Weight
     *
     * @param weight
     *
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * returns toString
     *
     * @return string
     *
     */
    @Override
    public String toString() {
        String string = "Oval: Anchor: " + anchor.toString() + " Width: " + width + " Height: " + height + " Weight: " + weight;
        return string;
    }
    
    @Override
    public void paintUsing(IPaintable paintable) {
        paintable.paint(this);
    }
}
