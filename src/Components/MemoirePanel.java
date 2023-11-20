package Components;

import Models.Memoire;
import Models.Student;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MemoirePanel extends JPanel {

    public MemoirePanel(Memoire memoire, boolean disabled, CallBack callBack) {
        LabeledField title = new LabeledField("Titre: ", memoire.title, disabled);

        LabeledField prof = new LabeledField("Encadreur: ", memoire.professor.nom + " " + memoire.professor.prenom, disabled);

        LabeledField year = new LabeledField("Annee: ",  Integer.toString(memoire.date), disabled);

        this.add(title);
        this.add(prof);
        this.add(year);

        int counter = 4;

        for (Student author: memoire.authors) {
            counter++;
            LabeledField authorField = new LabeledField("Auteur: ", author.nom + " " + author.prenom, disabled);
            this.add(authorField);
        }

        this.setLayout(new GridLayout(counter, 1));

        JPanel buttonsPanel = new JPanel();
        JButton confirm = new JButton("confirm");
        JButton cancel = new JButton("annuler");

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(confirm);
        buttonsPanel.add(cancel);

        confirm.addActionListener(e -> {
            callBack.onSuccess();
        });

        cancel.addActionListener(e -> {
            callBack.onCancel();
        });

        this.add(buttonsPanel);
    }

    public MemoirePanel(CallBack callBack) {
        LabeledField title = new LabeledField("Titre: ");

        LabeledField prof = new LabeledField("Encadreur: ");

        LabeledField year = new LabeledField("Annee: ");

        LabeledField author = new LabeledField("Auteur: ");

        JPanel buttonsPanel = new JPanel();
        JButton confirm = new JButton("confirm");
        JButton cancel = new JButton("annuler");

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(confirm);
        buttonsPanel.add(cancel);

        confirm.addActionListener(e -> {
            callBack.onSuccess();
        });

        cancel.addActionListener(e -> {
            callBack.onCancel();
        });

        this.setLayout(new GridLayout(5, 1));

        this.add(title);
        this.add(prof);
        this.add(year);
        this.add(author);
        this.add(buttonsPanel);
    }

    public interface CallBack {
        void onSuccess();
        void onFailure();
        void onCancel();
    }
}
