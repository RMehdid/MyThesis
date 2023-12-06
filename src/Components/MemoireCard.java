package Components;

import Models.Memoire;
import Models.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MemoireCard extends JPanel implements ActionListener {

    Memoire memoire;
    JLabel title;
    JLabel author;
    JLabel year;
    JButton openButton = new JButton("OPEN");
    JPanel leftPanel = new JPanel(new GridLayout(2, 1));
    JPanel rightPanel = new JPanel(new GridLayout(2, 1));


    public MemoireCard(Memoire memoire) {
        this.memoire = memoire;
        setLayout();
        setComponents(memoire);
        addActionListener();
        addComponents();
    }

    void setLayout() {
        setBorder(new EmptyBorder(8, 8, 8, 8));
        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 8));
    }
    void setComponents(Memoire memoire) {
        title = new JLabel("Title: " + memoire.title);
        Student firstAuthor = Arrays.stream(memoire.authors).findFirst().orElse(null);
        author = new JLabel("Author: " + (firstAuthor != null ? firstAuthor.nom + " " + firstAuthor.prenom : "Unknown"));
        year = new JLabel("Year: " + memoire.date);
    }
    void addComponents() {
        leftPanel.add(title);
        leftPanel.add(author);
        rightPanel.add(year);
        rightPanel.add(openButton);

        this.add(leftPanel);
        this.add(rightPanel);
    }

    void addActionListener() {
        openButton.addActionListener(this);
    }

    @Override
    public String toString() {
        return "MemoireCard{" +
                "title=" + title.getText() +
                ", author=" + author.getText() +
                ", year=" + year.getText() +
                '}';
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (memoire.pdfUrl != null) {
            openPdf(memoire.pdfUrl);
        } else {
            JOptionPane.showMessageDialog(MemoireCard.this, "An error occurred, empty location");
        }
    }

    private void openPdf(String pdfPath) {
        try {
            Desktop.getDesktop().open(new File(pdfPath));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MemoireCard.this, "An error occurred, couldn't find file at selected location");
        }
    }
}
