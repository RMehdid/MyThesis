package MyThesis;

import javax.swing.*;
import java.awt.*;

public class VisualizationBar extends JPanel {
    VisualizationBar() {
        this.setPreferredSize(new Dimension(200, 100));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createTitledBorder("Barre visualisation "));
    }
}
