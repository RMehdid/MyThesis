package Auth;

import Components.InlineField;
import Auth.Components.InlinePassword;
import Auth.Service.DBConnector;
import Models.Admin;
import Models.Professor;
import Models.Student;
import MyThesis.MyThesis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    InlineField matriculeField = new InlineField("MATRICULE");
    InlinePassword passwordField = new InlinePassword("PASSWORD");
    JButton loginButton = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGNUP");
    JCheckBox showPassword = new JCheckBox("Show Password");

    public AuthFrame() {
        setLayoutManager();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(new GridLayout(4, 1));
        container.setSize(370, 400);
    }
    public void addComponentsToContainer() {
        container.add(matriculeField);
        container.add(passwordField);
        container.add(showPassword);

        Container buttons = new Container();
        buttons.setLayout(new FlowLayout());
        buttons.add(loginButton);
        buttons.add(signupButton);

        container.add(buttons);
    }
    public void addActionEvent() {
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        showPassword.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            Long id = Long.parseLong(matriculeField.getText());
            String password = passwordField.getText();
            try {
                this.login(id, password);
            } catch(Exception error) {
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        }

        if (e.getSource() == signupButton) {
            dispose();

            SignupFrame frame = new SignupFrame();
            frame.setTitle("Signup Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    private void login(Long id, String password) throws Exception {
        switch (DBConnector.login(id, password)) {
            case "Student" -> {
                Student student = DBConnector.getStudent(id);
                MyThesis myThesis = new MyThesis(student);
                myThesis.setVisible(true);
            }

            case "Prof" -> {
                Professor professor = DBConnector.getProfessor(id);
                MyThesis myThesis = new MyThesis(professor);
                myThesis.setVisible(true);
            }

            case "Admin" -> {
                Admin admin = DBConnector.getAdmin(id);
                MyThesis myThesis = new MyThesis(admin);
                myThesis.setVisible(true);
            }
        }
    }
}