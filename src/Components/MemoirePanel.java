package Components;

import Models.Memoire;

import javax.swing.*;
import java.awt.*;

public class MemoirePanel extends JPanel {

    public MemoirePanel(Memoire memoire, boolean disabled, CallBack callBack) {
        LabeledField title = new LabeledField("Titre: ", memoire.title, disabled);

        LabeledField prof = new LabeledField("Encadreur: ", memoire.prof, disabled);

        LabeledField year = new LabeledField("Annee: ", memoire.year, disabled);

        LabeledField author = new LabeledField("Auteur: ", memoire.author, disabled);

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
