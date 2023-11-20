package Components;

import javax.swing.*;
import java.awt.*;

public class LabeledField extends JPanel  {

    private JTextField textField;

    public LabeledField(String label, String value, boolean disabled) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set the layout manager first

        JLabel labelComponent = new JLabel(label);
        add(labelComponent);

        textField = new JTextField(value, 10);
        textField.setEnabled(!disabled);
        add(textField);
    }

    public LabeledField(String label) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set the layout manager first

        JLabel labelComponent = new JLabel(label);
        add(labelComponent);

        textField = new JTextField(10);
        add(textField);
    }

    public String getText() {
        if (textField != null) {
            return textField.getText();
        }
        return "null";
    }
}
