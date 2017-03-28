/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.unmodifiableObservableList;
import javafx.collections.ObservableList;

/**
 *
 * @author piete
 */
public class Drawing extends DrawingItem implements Serializable {

    private List<DrawingItem> items = new ArrayList<>();
    private transient ObservableList<DrawingItem> observableList;
    private String name;
    IPaintable painting;

    public Drawing(String naam) {
        name = naam;
        observableList = FXCollections.observableList(items);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void paintUsing(IPaintable paintable) {
        for (DrawingItem drawingItem : observableList) {
            drawingItem.paintUsing(paintable);
        }
        observableList = FXCollections.observableList(items);
    }

    public ObservableList<DrawingItem> itemsToObserve() {
        return unmodifiableObservableList(observableList);
    }

    public void setItems(ObservableList<DrawingItem> list) {
        //this.observableList = list;
    }

    public void addItem(DrawingItem d) {
        observableList.add(d);
    }
    public void removeItem(int index) {
        observableList.remove(index);
    }

    @Override
    public Point getAnchor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Image: " + name + " Containing" + items.size() + " items";
    }
}
