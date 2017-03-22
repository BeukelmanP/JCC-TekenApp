/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author piete
 */
public class JavaFXPaintable implements IPaintable {

    private GraphicsContext gc;

    public JavaFXPaintable(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void paint(Oval oval) {
        if (oval.getColor() == null) {
            gc.strokeOval(oval.getAnchor().getX(), oval.getAnchor().getY(), oval.getWidth(), oval.getHeight());
        } else {
            gc.setFill(Color.web(oval.getColor().toString()));
            gc.fillOval(oval.getAnchor().getX(), oval.getAnchor().getY(), oval.getWidth(), oval.getHeight());
        }
        //gc.strokeRect(oval.getAnchor().getX(),oval.getAnchor().getY() , oval.getWidth(), oval.getHeight());
    }

    @Override
    public void paint(Polygon polygon) {
        Point[] points = polygon.getVertices();
        double[] xcoord = new double[points.length];
        double[] ycoord = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            // or:
            xcoord[i] = points[i].getX();
            ycoord[i] = points[i].getY();  // java 1.5+ style (outboxing)

        }
        if (polygon.getColor() == null) {
            gc.strokePolygon(xcoord, ycoord, points.length);

        } else {
            gc.setFill(javafx.scene.paint.Color.web(polygon.getColor().toString()));
            gc.fillPolygon(xcoord, ycoord, points.length);
        }
        gc.strokePolygon(xcoord, ycoord, points.length);
        //gc.strokeRect(polygon.getAnchor().getX(),polygon.getAnchor().getY() , polygon.getWidth(), polygon.getHeight());
    }

    @Override
    public void paint(PaintedText text) {
        if (text.getColor() == null) {
            gc.setFill(javafx.scene.paint.Color.web("BLACK"));
            gc.fillText(text.getContent(), text.getAnchor().getX(), text.getAnchor().getY());

        } else {
            gc.setFill(javafx.scene.paint.Color.web(text.getColor().toString()));
            gc.fillText(text.getContent(), text.getAnchor().getX(), text.getAnchor().getY());
        }
    }

    @Override
    public void paint(Image image2) {
        javafx.scene.image.Image image = null;
        image = new javafx.scene.image.Image((String)image2.getFile().getPath());
        gc.drawImage(image, image2.getAnchor().getX(), image2.getAnchor().getY(), image2.getWidth(), image2.getHeight());

    }

    @Override
    public GraphicsContext gc() {
        return gc;
    }
}
