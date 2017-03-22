/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paintapp;

import drawing.domain.Drawing;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piete
 */
public class SerializationMediator implements PersistancyMediator {

    Properties props;

    public Drawing load(String nameDrawing) {
        Drawing drawing = null;
        try {
            FileInputStream fileIn = new FileInputStream(nameDrawing);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            drawing = (Drawing) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {

            i.printStackTrace();

            return null;

        } catch (ClassNotFoundException c) {

            System.out.println("Drawing not found");

            c.printStackTrace();

            return null;

        }

        System.out.println("Drawing loaded");

        return drawing;
    }

    public boolean save(Drawing drawing) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("C:/Users/piete/Documents/serialized.ser"));
            out.writeObject(drawing);
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerializationMediator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(SerializationMediator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean init(Properties props) {
        return true;
    }
}
