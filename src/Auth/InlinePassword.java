package Auth;

import javax.swing.*;
import java.awt.*;

public class InlinePassword extends JPanel {

    JPasswordField textField;
    InlinePassword(String string) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(string);
        textField = new JPasswordField(15);
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

    public void setEchoChar(char c) {
        this.textField.setEchoChar(c);
    }
}
