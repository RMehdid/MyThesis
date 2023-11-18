package Components;

import Models.Memoire;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MemoireCard extends JPanel {
    JLabel title;
    JLabel author;
    JLabel year;
    JLabel id;
    public MemoireCard(Memoire memoire) {
        title = new JLabel("Title: " + memoire.title);
        author = new JLabel("Author: " + memoire.author);
        year = new JLabel("Year: " + memoire.year);
        id = new JLabel("ID: " + memoire.id);

        // Create two JPanels to hold labels on the left and right sides
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));

        // Add labels to the left panel
        leftPanel.add(title);
        leftPanel.add(author);

        // Add labels to the right panel
        rightPanel.add(year);
        rightPanel.add(id);

        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 8));

        this.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Add the left and right panels to the MemoireCard
        this.add(leftPanel);
        this.add(rightPanel);
    }

    @Override
    public String toString() {
        return "MemoireCard{" +
                "title=" + title.getText() +
                ", author=" + author.getText() +
                ", year=" + year.getText() +
                ", id=" + id.getText() +
                '}';
    }
}
