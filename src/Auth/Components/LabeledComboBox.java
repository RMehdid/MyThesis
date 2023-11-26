package Auth.Components;

import Models.Speciality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LabeledComboBox<T> extends JPanel {
    JComboBox<T> comboBox;
    public LabeledComboBox(String string, T[] values) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        comboBox = new JComboBox<>(values);

        add(label);
        add(comboBox);
    }

    public String getText() {
        if(comboBox != null) {
            return Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        }

        return "error";
    }

    public void setSelectedItem(T value) {
        this.comboBox.setSelectedItem(value);
    }

    public T getSelectedItem() {
        return (T) this.comboBox.getSelectedItem();
    }
}
