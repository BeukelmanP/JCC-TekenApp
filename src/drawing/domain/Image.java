/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

import java.io.File;

/**
 *
 * @author piete
 */
public class Image extends DrawingItem {

    public Image(File file, Point anchor, double width, double height) {
        this.file = file;
        this.anchor = anchor;
        this.width = width;
        this.height = height;
    }

    private File file;
    private Point anchor;
    private double width;
    private double height;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public Point getAnchor() {
        return anchor;
    }

    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        String string = "Image: " + file.getPath() + " Anchor: " + anchor.toString() + " Width: " + width + " Height: " + height;
        return string;
    }
    
    @Override
    public void paintUsing(IPaintable paintable) {
        paintable.paint(this);
    }

}
