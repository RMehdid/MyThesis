package Components;

import Models.Memoire;

import javax.swing.*;
import java.awt.*;

public class MemoireCard extends JPanel {

    MemoireCard(Memoire memoire) {
        JLabel title = new JLabel(memoire.title);
        JLabel author = new JLabel(memoire.author);
        JLabel year = new JLabel(memoire.year);
        JLabel id = new JLabel(memoire.id);

        this.setLayout(new GridLayout(2, 2));
    }
}
