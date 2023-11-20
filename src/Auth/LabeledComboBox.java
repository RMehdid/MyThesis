package Auth;

import Models.Speciality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabeledComboBox<T> extends JPanel {
    JComboBox<T> comboBox;
    LabeledComboBox(String string, T[] values) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        comboBox = new JComboBox<>(values);

        add(label);
        add(comboBox);
    }

    protected String getText() {
        if(comboBox != null) {
            return comboBox.getSelectedItem().toString();
        }

        return "error";
    }
}
