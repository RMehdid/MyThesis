package Components;

import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoirePanel extends JPanel implements ActionListener {

    User user;

    InlineField title;
    InlineField prof;
    InlineField year;

    InlineField author1;
    InlineField author2;
    InlineField author3;

    JComboBox<Level> levels;

    ResumeField resumeField;

    String pdfPath;

    JButton confirm;
    JButton cancel;


    public MemoirePanel(User user, Memoire memoire, boolean disabled, CallBack callBack) {
        this.user = user;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        title = new InlineField("Titre: ", memoire.title, disabled);

        prof = new InlineField("Encadreur: ", memoire.professor.nom + " " + memoire.professor.prenom, disabled);

        year = new InlineField("Annee: ",  Integer.toString(memoire.date), disabled);

        this.add(title);
        this.add(prof);
        this.add(year);

        if (memoire.authors.length > 1) {
            author3 = new InlineField("Auteur 1: ", memoire.authors[0].id, memoire.authors[0].nom + " " + memoire.authors[0].prenom, disabled);
        } else {
            author1 = new InlineField("Auteur 1: ", "", disabled);
        }

        if (memoire.authors.length > 2) {
            author3 = new InlineField("Auteur 2: ", memoire.authors[1].id, memoire.authors[1].nom + " " + memoire.authors[1].prenom, disabled);
        } else {
            author2 = new InlineField("Auteur 2: ", "", disabled);
        }

        if (memoire.authors.length > 3) {
            author3 = new InlineField("Auteur 3: ", memoire.authors[2].id, memoire.authors[2].nom + " " + memoire.authors[2].prenom, disabled);
        } else {
            author3 = new InlineField("Auteur 3: ", "", disabled);
        }

        levels = new JComboBox<>(Level.values());
        levels.setSelectedItem(memoire.level);
        levels.setEnabled(!disabled);

        resumeField = new ResumeField("Resume", memoire.resume, disabled);

        JPanel pdfPanel = new JPanel();
        JLabel pdfFileLabel = new JLabel();

        JButton choosePdfButton = new JButton("choose pdf");
        choosePdfButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(MemoirePanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {

                java.io.File selectedFile = fileChooser.getSelectedFile();

                pdfPath = selectedFile.getAbsolutePath();
            }
        });

        JButton showPdf = new JButton("show pdf");
        showPdf.addActionListener(e -> {

            if (!pdfPath.isEmpty()) {
                openPdf(pdfPath);
            } else {
                JOptionPane.showMessageDialog(MemoirePanel.this, "Please choose a PDF file first.");
            }
        });

        pdfPanel.setLayout(new FlowLayout());
        pdfPanel.add(pdfFileLabel);
        pdfPanel.add(choosePdfButton);
        pdfPanel.add(showPdf);

        this.add(author1);
        this.add(author2);
        this.add(author3);
        this.add(levels);
        this.add(resumeField);
        this.add(pdfPanel);

        JPanel buttonsPanel = new JPanel();
        confirm = new JButton("confirm");
        cancel = new JButton("annuler");

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(confirm);
        buttonsPanel.add(cancel);

        confirm.addActionListener(this);

        cancel.addActionListener(this);

        this.add(buttonsPanel);
    }

    public MemoirePanel(User user, CallBack callBack) {
        this.user = user;

        title = new InlineField("Titre: ");

        prof = new InlineField("Encadreur: ");

        year = new InlineField("Annee: ");

        author1 = new InlineField("Auteur 1: ");
        author2 = new InlineField("Auteur 2: ");
        author3 = new InlineField("Auteur 3: ");

        levels = new JComboBox<>(Level.values());

        resumeField = new ResumeField("Resume");

        JPanel pdfPanel = new JPanel();
        pdfPanel.setLayout(new FlowLayout());
        JLabel pdfFileLabel = new JLabel();

        JButton choosePdfButton = new JButton("choose pdf");
        choosePdfButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(MemoirePanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {

                java.io.File selectedFile = fileChooser.getSelectedFile();

                pdfPath = selectedFile.getAbsolutePath();
            }
        });

        JButton showPdf = new JButton("show pdf");
        showPdf.addActionListener(e -> {

            if (!pdfPath.isEmpty()) {
                openPdf(pdfPath);
            } else {
                JOptionPane.showMessageDialog(MemoirePanel.this, "Please choose a PDF file first.");
            }
        });

        pdfPanel.add(pdfFileLabel);
        pdfPanel.add(choosePdfButton);
        pdfPanel.add(showPdf);



        JPanel buttonsPanel = new JPanel();
        confirm = new JButton("confirm");
        cancel = new JButton("annuler");

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(confirm);
        buttonsPanel.add(cancel);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(prof);
        this.add(year);
        this.add(author1);
        this.add(author2);
        this.add(author3);
        this.add(levels);
        this.add(resumeField);
        this.add(pdfPanel);
        this.add(buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            if(user instanceof Admin) {
                String title = this.title.getText();
                Long professorId = Long.valueOf(this.prof.getText());
                int date = Integer.parseInt(this.year.getText());
                List<Long> studentsIds = new ArrayList<>();

                if (!this.author1.getText().isEmpty()) {
                    studentsIds.add(Long.valueOf(this.author1.getText()));
                }
                if (!this.author2.getText().isEmpty()) {
                    studentsIds.add(Long.valueOf(this.author2.getText()));
                }
                if (!this.author3.getText().isEmpty()) {
                    studentsIds.add(Long.valueOf(this.author3.getText()));
                }

                Long[] studentsIdsArray = studentsIds.toArray(new Long[0]);

                Level level = (Level) this.levels.getSelectedItem();
                String resume = this.resumeField.getText();

                try {
                    ((Admin) user).createMemoire(title, professorId, date, studentsIdsArray, level, resume, pdfPath);
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(MemoirePanel.this, error.getMessage());
                }
            }
        }
    }

    private void openPdf(String pdfPath) {
        try {
            Desktop.getDesktop().open(new File(pdfPath));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MemoirePanel.this, e.getMessage());
        }
    }

    public interface CallBack {
        void onSuccess();
        void onFailure();
        void onCancel();
    }
}
