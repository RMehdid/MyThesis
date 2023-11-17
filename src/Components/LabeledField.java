package Components;

import javax.swing.*;
import java.awt.*;

public class LabeledField extends JLabel  {

    public LabeledField(String label, String value, boolean disabled) {
        JLabel myLabel = new JLabel(label);
        JTextField textField = new JTextField(value, 10);

        textField.setEnabled(!disabled);
        this.add(myLabel);
        this.add(textField);
        this.setLayout(new GridLayout(2, 1));
    }

    public LabeledField(String label) {
        JLabel myLabel = new JLabel(label);
        JTextField textField = new JTextField(10);

        this.add(myLabel);
        this.add(textField);
        this.setLayout(new GridLayout(2, 1));
    }
}
