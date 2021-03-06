/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paintapp;

import drawing.domain.Color;
import drawing.domain.Drawing;
import drawing.domain.DrawingItem;
import drawing.domain.Image;
import drawing.domain.Oval;
import drawing.domain.PaintedText;
import drawing.domain.Point;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class StartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField XField;
    @FXML
    private TextField YField;
    @FXML
    private TextField WField;
    @FXML
    private TextField HField;
    @FXML
    private TextField imgURL;
    @FXML
    private TextField TextField;
    @FXML
    private TextField PointXField;
    @FXML
    private TextField PointYField;
    @FXML
    private TextField DBText;
    @FXML
    private ComboBox OvalColorField;
    @FXML
    private ComboBox PointsField;
    @FXML
    private ComboBox cmbBox;
    @FXML
    private Button AddOvalButton;
    @FXML
    private Button AddImageButton;
    @FXML
    private Button AddTextButton;
    @FXML
    private Button AddPointButton;
    @FXML
    private Button AddPolygonButton;
    @FXML
    private Button SaveFileButton;
    @FXML
    private Button LoadFileButton;
    @FXML
    private Button NewFileButton;
    @FXML
    private Button LoadDBButton;
    @FXML
    private Button SaveDBButton;
    @FXML
    private Button btnDeleteItem;

    private TekenAppFX tekenApp;
    private SerializationMediator FileSaver = new SerializationMediator();
    private DatabaseMediator DatabaseSaver = new DatabaseMediator();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tekenApp = TekenAppFX.getInstance();
        ObservableList<String> colors = observableArrayList("RED", "GREEN", "WHITE", "BLUE", "BLACK");
        OvalColorField.setItems(colors);
        cmbBox.setItems(tekenApp.getDrawing().itemsToObserve());
    }

    @FXML

    public void btnAddOvalClick() {
        Color c = addColor((String) OvalColorField.getValue());
        boolean overlaps = false;
        Oval a = new Oval(new Point(Double.parseDouble(XField.getText()), Double.parseDouble(YField.getText())), Double.parseDouble(WField.getText()), Double.parseDouble(HField.getText()), 0.0, c);
        for (DrawingItem b : tekenApp.getDrawing().itemsToObserve()) {
            overlaps = a.overlaps(b);
            if (overlaps) {
                JOptionPane.showMessageDialog(null, "Overlap detected", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        tekenApp.addDrawingItem(a);
        tekenApp.draw();
        //setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void btnAddTextClick() {
        Color c = null;
        if (OvalColorField.getValue() != null) {
            c = addColor((String) OvalColorField.getValue());
        }
        boolean overlaps = false;
        PaintedText a = new PaintedText(TextField.getText(), "Arial", new Point(Double.parseDouble(XField.getText()), Double.parseDouble(YField.getText())), 0.0, 0.0, c);
        for (DrawingItem b : tekenApp.getDrawing().itemsToObserve()) {
            overlaps = a.overlaps(b);
            if (overlaps) {
                JOptionPane.showMessageDialog(null, "Overlap detected", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        tekenApp.addDrawingItem(a);
        tekenApp.draw();
        //setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void AddImageButtonClick() {
        boolean overlaps = false;
        Image a = new Image(new File(imgURL.getText(), imgURL.getText()), new Point(Double.parseDouble(XField.getText()), Double.parseDouble(YField.getText())), Double.parseDouble(WField.getText()), Double.parseDouble(HField.getText()));
        for (DrawingItem b : tekenApp.getDrawing().itemsToObserve()) {
            overlaps = a.overlaps(b);
            if (overlaps) {
                JOptionPane.showMessageDialog(null, "Overlap detected", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        tekenApp.addDrawingItem(a);
        tekenApp.draw();
        //setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void btnSaveFileClick() {
        FileSaver.save(tekenApp.getDrawing());
    }

    public void btnLoadFileClick() {
        tekenApp.setDrawing(FileSaver.load("C:/Users/piete/Documents/serialized.ser"));
        tekenApp.draw();
        setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void btnNewFileClick() {
        tekenApp.setDrawing(new Drawing("tekening"));
        tekenApp.draw();
        setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void btnDBLoadClick() throws SQLException {
        tekenApp.setDrawing(DatabaseSaver.load(DBText.getText()));
        tekenApp.draw();
        setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void btnDBsaveClick() throws SQLException {
        tekenApp.getDrawing().setName(DBText.getText());
        boolean saved = DatabaseSaver.save(tekenApp.getDrawing());
        if (saved) {
            JOptionPane.showMessageDialog(null, "Saved Succesful", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Not saved", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void btnDeleteItemClick() {
        tekenApp.removeDrawingItem(cmbBox.getSelectionModel().getSelectedIndex());
        tekenApp.draw();
        //setItems(tekenApp.getDrawing().itemsToObserve());
    }

    public void setItems(ObservableList<DrawingItem> list) {
        cmbBox.setItems(list);
    }

    public Color addColor(String color) {
        switch (color) {
            case "RED":
                return Color.RED;
            case "GREEN":
                return Color.GREEN;
            case "WHITE":
                return Color.WHITE;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            default:
                return null;
        }
    }

    public String translateColor(Color a) {
        if (a == null) {
            a = Color.UNSPECIFIED;
        }
        switch (a) {
            case RED:
                return "RED";
            case GREEN:
                return "GREEN";
            case WHITE:
                return "WHITE";
            case BLUE:
                return "BLUE";
            case BLACK:
                return "BLACK";
            default:
                return "test";
        }
    }

}
