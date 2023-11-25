package MyThesis;

import Components.InlineField;
import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileBar extends JPanel implements ActionListener {

    boolean isModifying = false;

    User user;
    JLabel iconLabel = new JLabel(new ImageIcon("Assets/imageIcon.png"));
    JLabel fullname;
    JLabel speciality;
    JLabel userType;

    InlineField familyNameField;
    InlineField firstnameField;
    JComboBox<Speciality> specialityComboBox;

    JButton modifyButton = new JButton("modify");
    JButton cancelButton = new JButton("cancel");

    ProfileBar(User user) {
        this.user = user;

        setComponents(user);
    }

    private void setLayout() {
        this.setPreferredSize(new Dimension(200, 100));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createTitledBorder("Profile Bar"));
    }

    private void setComponents(User user) {
        setLayout();
        add(iconLabel);

        if (isModifying) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setModifyingComponents(user);
            add(familyNameField);
            add(firstnameField);
            if(!(user instanceof Admin)) {
                add(specialityComboBox);
            }
            add(cancelButton);

        } else {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setNotModifyingComponents(user);
            add(iconLabel);
            add(fullname);
            if(!(user instanceof Admin)) {
                add(speciality);
            }
            add(userType);
        }

        if(!(user instanceof Professor)) {
            add(modifyButton);
        }

        setActionListeners();
        repaint();
        revalidate();
    }

    private void setNotModifyingComponents(User user) {
        fullname = new JLabel(user.nom + " " + user.prenom);

        if (user instanceof Student) {
            speciality = new JLabel(((Student) user).speciality.toString());
            userType = new JLabel("Student");
        } else if (user instanceof Professor) {
            speciality = new JLabel(((Professor) user).speciality.toString());
            userType = new JLabel("Professor");
        } else {
            userType = new JLabel("Admin");
        }
    }

    private void setModifyingComponents(User user) {
        familyNameField = new InlineField("family name", user.nom, false);
        firstnameField = new InlineField("first name", user.prenom, false);
        specialityComboBox = new JComboBox<>(Speciality.values());
    }

    private void setActionListeners() {
        modifyButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == modifyButton) {
            if(isModifying) {
                if(user instanceof Admin) {
                    User modifiedUser = new Admin(user.id, familyNameField.getText(), firstnameField.getText());
                    updateUser(modifiedUser);
                } else if(user instanceof Student) {
                    User modifiedUser = new Student(user.id, familyNameField.getText(), firstnameField.getText(), (Speciality) specialityComboBox.getSelectedItem());
                    updateUser(modifiedUser);
                }
                isModifying = false;
                setComponents(user);
            } else {
                isModifying = true;
            }
        } else if (e.getSource() == cancelButton) {
            isModifying = false;
        }
        removeAll();
        setComponents(user);
        System.out.println(isModifying);
    }

    private void updateUser(User user) {
        this.user = user;
    }
}
