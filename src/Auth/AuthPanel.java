package Auth;

import Components.LabeledField;
import Components.LabeledPassword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthPanel extends JPanel implements ActionListener {

    LabeledField username;
    LabeledPassword password;

    AuthPanel() {
        username = new LabeledField("username: ");
        password = new LabeledPassword("password: ");

        JButton signInButton = new JButton("se connecter");
        JButton signUpButton = new JButton("s'enregistrer");

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.equals("section.io") && password.equals("123"))
            JOptionPane.showMessageDialog(null, "Login Successful");
        else
            JOptionPane.showMessageDialog(null, "Username or Password mismatch ");
    }
}
