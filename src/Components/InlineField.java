package Components;

import javax.swing.*;
import java.awt.*;

public class InlineField extends JPanel {

    JTextField textField;

    public InlineField(String string) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JTextField(15);
        textField.setSize(new Dimension(130, 30));

        add(label);
        add(textField);
    }

    public InlineField(String string, String value, boolean disabled) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JTextField(15);
        textField.setText(value);
        textField.setEnabled(!disabled);
        textField.setSize(new Dimension(130, 30));

        add(label);
        add(textField);
    }

    public InlineField(String label, Long id, String value, boolean disabled) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel jLabel = new JLabel(label);
        textField = new JTextField(15);
        textField.setText(String.valueOf(id));
        textField.setEnabled(!disabled);
        textField.setSize(new Dimension(130, 30));
        JLabel nameLabel = new JLabel(value);
        nameLabel.setForeground(Color.gray);

        add(jLabel);
        add(textField);
        add(nameLabel);
    }

    public String getText() {
        if(textField != null) {
            return textField.getText();
        }

        return "error";
    }
}