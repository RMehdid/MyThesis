package Components;

import javax.swing.*;
import java.awt.*;

public class InlineField extends JPanel {

    JTextField textField;

    public InlineField(String string) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JTextField(15);
        textField.setPreferredSize(new Dimension(130, 30));

        add(label);
        add(textField);
    }

    public InlineField(String string, String value, boolean disabled) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JTextField(15);
        textField.setText(value);
        textField.setEnabled(!disabled);
        textField.setPreferredSize(new Dimension(130, 30));

        add(label);
        add(textField);
    }

    public String getText() {
        if(textField != null) {
            return textField.getText();
        }

        return "error";
    }
}