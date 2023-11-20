package Auth;

import Models.Speciality;
import Models.Student;
import MyThesis.MyThesis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    InlineField matriculePanel = new InlineField("MATRICULE");
    InlineField familyNamePanel = new InlineField("FAMILY NAME");
    InlineField firstnamePanel = new InlineField("FIRST NAME");
    LabeledComboBox<Speciality> specialityField = new LabeledComboBox<>("SPECIALITY", Speciality.allCases);
    InlinePassword passwordPanel = new InlinePassword("PASSWORD");
    InlinePassword rePasswordPanel = new InlinePassword("RE-ENTER PASSWORD");
    JButton loginButton = new JButton("SIGNUP");
    JButton cancelButton = new JButton("CANCEL");

    public SignupFrame() {
        setLayoutManager();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(new GridLayout(7, 1));
    }
    public void addComponentsToContainer() {
        container.add(matriculePanel);
        container.add(familyNamePanel);
        container.add(firstnamePanel);
        container.add(specialityField);
        container.add(passwordPanel);
        container.add(rePasswordPanel);

        Container buttons = new Container();
        buttons.setLayout(new FlowLayout());
        buttons.add(loginButton);
        buttons.add(cancelButton);

        container.add(buttons);
    }
    public void addActionEvent() {
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            long id = Long.parseLong(matriculePanel.getText());
            String password = passwordPanel.getText();
            String rePassword = rePasswordPanel.getText();

            if (rePassword.equals(password)) {
                try {
                    this.register(id, password);
                } catch(Exception error) {
                    JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            }
        }

        else if (e.getSource() == cancelButton) {
            dispose();

            AuthFrame frame = new AuthFrame();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        }
    }

    private void register(long id, String password) throws Exception {

        if (!DBConnector.isUsernameExists(id)) {
            if (DBConnector.registerLogin(id, password, "Student")) {
                Student student = new Student(id, familyNamePanel.getText(), firstnamePanel.getText(), Speciality.valueOf(specialityField.getText()));
                if(DBConnector.register(student)) {
                    dispose();
                    MyThesis myThesis = new MyThesis(student);
                    myThesis.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Field error");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username Already exists");
        }
    }
}

class SignUpMain {
    public static void main(String[] args) {
        AuthFrame frame = new AuthFrame();
        frame.setTitle("SignUp Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}