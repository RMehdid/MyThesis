package Auth;

import javax.swing.*;
import java.awt.*;

public class InlineField extends JPanel {

    JTextField textField;

    InlineField(String string) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JTextField(15);
        textField.setPreferredSize(new Dimension(130, 30));

        add(label);
        add(textField);
    }

    protected String getText() {
        if(textField != null) {
            return textField.getText();
        }

        return "error";
    }
}