package Components;

import javax.swing.*;
import java.awt.*;

public class ResumeField extends JPanel {
    JTextArea resumeField;

    public ResumeField(String string) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(string);
        resumeField = new JTextArea();
        resumeField.setSize(new Dimension(130, 100));
        resumeField.setLineWrap(true); // Enable line wrapping
        resumeField.setWrapStyleWord(true);

        add(label);
        add(resumeField);
    }

    public ResumeField(String string, String value, boolean disabled) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(string);
        resumeField = new JTextArea();
        resumeField.setText(value);
        resumeField.setEnabled(!disabled);
        resumeField.setSize(new Dimension(130, 30));

        add(label);
        add(resumeField);
    }

    public String getText() {
        if(resumeField != null) {
            return resumeField.getText();
        }

        return "error";
    }
}
