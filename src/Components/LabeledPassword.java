package Components;

import javax.swing.*;
import java.awt.*;

public class LabeledPassword extends JLabel {

        public LabeledPassword(String label) {
            JLabel myLabel = new JLabel(label);
            JPasswordField passwordField = new JPasswordField(10);

            this.add(myLabel);
            this.add(passwordField);
            this.setLayout(new GridLayout(2, 1));
        }
}
