/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paintapp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import drawing.domain.Color;
import drawing.domain.Drawing;
import drawing.domain.DrawingItem;
import drawing.domain.Image;
import drawing.domain.Oval;
import drawing.domain.PaintedText;
import drawing.domain.Point;
import drawing.domain.Polygon;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author piete
 */
public class DatabaseMediator {

    Drawing drawing;
    Properties props;
    Connection con;

    public DatabaseMediator() {
        try {
            Driver myDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(myDriver);

            String URL = "jdbc:mysql://localhost/drawing";
            String USER = "root";
            String PASS = "";
            con = (Connection) DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection ok.");
        } catch (SQLException ex) {
            System.out.println("Connection Fail.");
        }
    }

    public Drawing load(String nameDrawing) throws SQLException {
        Drawing drawing1 = new Drawing("");
        try {

            Statement st = con.createStatement();
            String sql = ("SELECT * FROM drawing WHERE Name = " + "'" + nameDrawing + "'");
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                drawing1.setName(rs.getString("Name"));
            }

        } catch (SQLException ex) {
            return null;
        }
        try {
            Statement st = con.createStatement();
            String sql = ("SELECT * FROM drawingitem WHERE DRAWING = " + "'" + drawing1.getName() + "'");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Point P = new Point(rs.getDouble("LOCX"), rs.getDouble("LOCY"));
                switch (rs.getString("TYPE")) {
                    case "OVAL":
                        drawing1.addItem(new Oval(P, rs.getDouble("WIDTH"), rs.getDouble("HEIGHT"), rs.getDouble("WEIGHT"), addColor(rs.getString("COLOR"))));
                        break;
                    case "IMAGE":
                        String s = rs.getString("IMAGEURL");
                        File f = new File(s, s);
                        drawing1.addItem(new Image(f, P, rs.getDouble("WIDTH"), rs.getDouble("HEIGHT")));
                        break;
                    case "TEXT":
                        drawing1.addItem(new PaintedText(rs.getString("TEXT"), rs.getString("FONT"), P, rs.getDouble("WIDTH"), rs.getDouble("HEIGHT"), addColor(rs.getString("COLOR"))));
                        break;
                    case "POLYGON":
                        Point[] p = getPolygonPoints(rs.getInt("PolygonID"));
                        drawing1.addItem(new Polygon(p, 0, 0, 0, addColor(rs.getString("COLOR"))));
                        break;
                    case "DRAWING":
                        drawing1.addItem(load(rs.getString("externalImage")));
                        break;

                }
            }
        } catch (SQLException ex) {
            return null;
        }

        return drawing1;
    }

    public Point[] getPolygonPoints(int PolygonID) throws SQLException {
        Statement st2 = con.createStatement();
        String sql2 = ("SELECT COUNT(*) FROM polpoints WHERE PolygonID = " + PolygonID + " ;");
        ResultSet rs2 = st2.executeQuery(sql2);
        int i = 1;
        while (rs2.next()) {
            i = rs2.getInt(1);
        }
        Point[] p = new Point[i];

        try {
            Statement st1 = con.createStatement();
            String sql1 = ("SELECT * FROM polpoints WHERE PolygonID = " + "'" + PolygonID + "'");
            ResultSet rs1 = st1.executeQuery(sql1);
            int a = 0;
            while (rs1.next()) {
                p[a] = new Point(rs1.getDouble("LOCX"), rs1.getDouble("LOCY"));
                a++;
            }

            return p;

        } catch (SQLException ex) {
            return null;
        }
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

    public boolean save(Drawing drawing) throws SQLException {
        try {
            Statement st = con.createStatement();
            String sql = "DELETE FROM drawing WHERE NAME = '" + drawing.getName() + "'";
            st.executeUpdate(sql);
            Statement st6 = con.createStatement();
            String sql6 = "DELETE FROM drawingitem WHERE DRAWING = '" + drawing.getName() + "'";
            st6.executeUpdate(sql6);
            System.out.println("deleted old save");
        } catch (SQLException ex) {
            System.out.println("no old save found");
        }

        try {
            Statement st1 = con.createStatement();
            String sql1 = "INSERT INTO drawing (NAME) VALUES('" + drawing.getName() + "')";
            st1.executeUpdate(sql1);
            for (DrawingItem i : drawing.itemsToObserve()) {
                String color = translateColor(i.getColor());
                if (i instanceof Oval) {
                    Statement st2 = con.createStatement();
                    String sql2 = "INSERT INTO drawingitem (DRAWING,TYPE,LOCX,LOCY,WIDTH,HEIGHT,COLOR) VALUES ('" + drawing.getName() + "','OVAL'," + i.getAnchor().getX() + "," + i.getAnchor().getY() + "," + i.getWidth() + "," + i.getHeight() + ",'" + color + "')";

                    st2.executeUpdate(sql2);
                } else if (i instanceof Image) {
                    Statement st2 = con.createStatement();
                    String sql2 = "INSERT INTO drawingitem (DRAWING,TYPE,LOCX,LOCY,WIDTH,HEIGHT,IMAGEURL) VALUES ('" + drawing.getName() + "','IMAGE'," + i.getAnchor().getX() + "," + i.getAnchor().getY() + "," + i.getWidth() + "," + i.getHeight() + ",'" + ((Image) i).getFile().getPath() + "')";
                    st2.executeUpdate(sql2);
                } else if (i instanceof PaintedText) {
                    Statement st2 = con.createStatement();
                    String sql2 = "INSERT INTO drawingitem (DRAWING,TYPE,LOCX,LOCY,COLOR,TEXT,FONT) VALUES ('" + drawing.getName() + "','TEXT'," + i.getAnchor().getX() + "," + i.getAnchor().getY() + ",'" + color + "','" + ((PaintedText) i).getContent() + "','" + ((PaintedText) i).getFontName() + "')";
                    st2.executeUpdate(sql2);
                } else if (i instanceof Polygon) {
                    Statement st2 = con.createStatement();
                    String sql2 = ("SELECT MAX(PolygonID) FROM polpoints ;");
                    ResultSet rs2 = st2.executeQuery(sql2);
                    int id = 1;
                    while (rs2.next()) {
                        id = rs2.getInt(1) + 1;
                    }

                    Statement st3 = con.createStatement();
                    String sql3 = "INSERT INTO drawingitem (DRAWING,TYPE,COLOR,POLYGONID) VALUES ('" + drawing.getName() + "','POLYGON','" + color + "'," + id + ")";
                    st3.executeUpdate(sql3);
                    Point[] points = ((Polygon) i).getVertices();

                    for (Point p : points) {
                        Statement st4 = con.createStatement();
                        String sql4 = "INSERT INTO polpoints (POLYGONID,LOCX,LOCY) VALUES (" + id + "," + p.getX() + "," + p.getY() + ")";
                        st4.executeUpdate(sql4);
                    }
                }
            }

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean init(Properties props) {
        return true;
    }
}
