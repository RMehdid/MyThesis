package Components;

import Models.Memoire;
import Models.Student;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MemoirePanel extends JPanel {

    public MemoirePanel(Memoire memoire, boolean disabled, CallBack callBack) {
        InlineField title = new InlineField("Titre: ", memoire.title, disabled);

        InlineField prof = new InlineField("Encadreur: ", memoire.professor.nom + " " + memoire.professor.prenom, disabled);

        InlineField year = new InlineField("Annee: ",  Integer.toString(memoire.date), disabled);

        this.add(title);
        this.add(prof);
        this.add(year);

        int counter = 4;

        for (Student author: memoire.authors) {
            counter++;
            InlineField authorField = new InlineField("Auteur: ", author.nom + " " + author.prenom, disabled);
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
        InlineField title = new InlineField("Titre: ");

        InlineField prof = new InlineField("Encadreur: ");

        InlineField year = new InlineField("Annee: ");

        InlineField author = new InlineField("Auteur: ");

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
