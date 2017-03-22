/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.domain;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author piete
 */
public interface IPaintable {
    public GraphicsContext gc();
    public  void paint(Oval oval);
    public  void paint(Polygon polygon);
    public  void paint(PaintedText text);
    public  void paint(Image image);
}
