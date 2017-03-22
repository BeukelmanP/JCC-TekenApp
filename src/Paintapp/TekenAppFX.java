/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paintapp;

import drawing.domain.Color;
import drawing.domain.Drawing;
import drawing.domain.DrawingItem;
import drawing.domain.IPaintable;
import drawing.domain.Image;
import drawing.domain.JavaFXPaintable;
import drawing.domain.Oval;
import drawing.domain.PaintedText;
import drawing.domain.Point;
import drawing.domain.Polygon;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author piete
 */
public class TekenAppFX extends Application {

    private static TekenAppFX instance;
    public Drawing drawing= new Drawing("Henks Tekening");
    IPaintable paintable;
    Canvas cvs;

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;
        Parent parent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Group root = new Group();
        cvs = new Canvas(600, 600);
        cvs.setLayoutX(0);
        cvs.setLayoutY(0);
        paintable = new JavaFXPaintable(cvs.getGraphicsContext2D());
        root.getChildren().add(cvs);
        root.getChildren().add(parent);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        /*
        Canvas cvs = new Canvas();
        StackPane root = new StackPane();
        root.getChildren().add(cvs);
        cvs.setLayoutX(0);
        cvs.setLayoutY(0);
        cvs.setHeight(400);
        cvs.setWidth(400);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Drawing");
        primaryStage.setScene(scene);
        primaryStage.show();
        drawing = new Drawing();
        paintable = new JavaFXPaintable(cvs.getGraphicsContext2D());
         
        PaintedText paintedtext = new PaintedText("Tekst Om te testen of dit werkt zoals moet", "Arial", new Point(30.0, 30.0), 20.0, 20.0,Color.RED);
        Oval oval = new Oval(new Point(100.0, 150.0), 100.0, 100.0, 100.0,Color.GREEN);

        Point[] points = new Point[3];
        points[0] = new Point(300.0, 200.0);
        points[1] = new Point(200.0, 80.0);
        points[2] = new Point(350.0, 210.0);
        Polygon polygon = new Polygon(points, 20.0, 100.0, 80.0,Color.BLUE);
        Image image = new Image(new File("https://bin.snmmd.nl/m/06pyh5bwam5x.jpg", "https://bin.snmmd.nl/m/06pyh5bwam5x.jpg"), new Point(500.0, 500.0), 100.0, 100.0);
        drawing.items.add(oval);
        drawing.items.add(polygon);
        drawing.items.add(paintedtext);
        drawing.items.add(image);

        draw();
*/
    }

    public void draw() {
        
        cvs.getGraphicsContext2D().clearRect(0, 0, 600, 600);
        drawing.paintUsing(paintable);
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public static TekenAppFX getInstance() {
        return instance;
    }

}
