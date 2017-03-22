/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paintapp;

import drawing.domain.Drawing;
import java.util.Properties;

/**
 *
 * @author piete
 */
public interface PersistancyMediator {
    public Drawing load(String nameDrawing);

    public boolean save(Drawing drawing);

    public boolean init(Properties props);
}
