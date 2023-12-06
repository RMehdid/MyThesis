package MyThesis;

import Components.InlineField;
import Models.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ProfileBar extends JPanel implements ActionListener {

    boolean isModifying = false;

    User user;
    JLabel fullname;
    JLabel speciality;
    JLabel userType;
    JLabel icon;
    InlineField familyNameField;
    InlineField firstnameField;
    JComboBox<Speciality> specialityComboBox;
    JPanel buttonsPanel;
    JButton modifyButton;
    JButton cancelButton = new JButton("Cancel");

    ProfileBar(User user) {
        this.user = user;
        setComponents();
    }

    private void setLayout() {
        this.setPreferredSize(new Dimension(300, 150));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createTitledBorder("Profile Bar"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void setComponents() {
        setLayout();

        if (isModifying) {
            setModifyComponents();
            addModifyComponents();
        } else {
            setReadComponents();
            addReadComponents();
        }

        setActionListeners();
        repaint();
        revalidate();
    }

    private void setReadComponents() {
        icon = new JLabel(loadImageIcon());
        fullname = new JLabel(user.nom + " " + user.prenom);
        if (user instanceof Student) {
            speciality = new JLabel(((Student) user).speciality.toString());
            userType = new JLabel("User Type: Student");
        } else if (user instanceof Professor) {
            speciality = new JLabel(((Professor) user).speciality.toString());
            userType = new JLabel("Professor");
        } else {
            userType = new JLabel("Admin");
        }
        modifyButton = new JButton("Modify");
    }
    private void setModifyComponents() {
        icon = new JLabel(loadImageIcon());
        familyNameField = new InlineField("Family Name", user.nom, false);
        firstnameField = new InlineField("First Name", user.prenom, false);

        if (user instanceof Student) {
            specialityComboBox = new JComboBox<>(Speciality.values());
            specialityComboBox.setSelectedItem(((Student) user).speciality);
        } else if (user instanceof Professor) {
            specialityComboBox = new JComboBox<>(Speciality.values());
            specialityComboBox.setSelectedItem(((Professor) user).speciality);
        }
        buttonsPanel = new JPanel();
        modifyButton = new JButton("Modify");
        cancelButton = new JButton("Cancel");
    }
    private void addReadComponents() {
        add(icon);
        add(fullname);
        add(Box.createVerticalStrut(10));
        if (!(user instanceof Admin)) {
            add(speciality);
            add(Box.createVerticalStrut(10));
        }
        add(userType);
        add(Box.createVerticalStrut(10));
        add(modifyButton);
    }
    private void addModifyComponents() {
        add(icon);
        add(familyNameField);
        add(Box.createVerticalStrut(10));
        add(firstnameField);
        add(Box.createVerticalStrut(10));
        if (!(user instanceof Admin)) {
            add(specialityComboBox);
            add(Box.createVerticalStrut(10));
        }
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(modifyButton);

        add(buttonsPanel);
    }
    private void setActionListeners() {
        modifyButton.addActionListener(this);
        if(isModifying) {
            cancelButton.addActionListener(this);
        }
    }
    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == modifyButton) {
            if (isModifying) {
                updateUser();
            }
            isModifying = !isModifying;
            removeAll();
            setComponents();
        } else if(e.getSource() == cancelButton) {
            isModifying = !isModifying;
            removeAll();
            setComponents();
        }
        System.out.println(isModifying);
    }
    private void updateUser() {
        if (user instanceof Admin) {
            user = new Admin(user.id, familyNameField.getText(), firstnameField.getText());
            try {
                ((Admin) user).modifyAdmin();
            } catch (Exception error){
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        } else if (user instanceof Student) {
            user = new Student(user.id, familyNameField.getText(), firstnameField.getText(),
                    (Speciality) specialityComboBox.getSelectedItem());
            try {
                ((Student) user).modifyStudent();
            } catch (Exception error){
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        } else if (user instanceof Professor) {
            user = new Professor(user.id, familyNameField.getText(), firstnameField.getText(),
                    (Speciality) specialityComboBox.getSelectedItem());
            try {
                ((Professor) user).modifyProfessor();
            } catch (Exception error){
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        }
    }

    private static @Nullable ImageIcon loadImageIcon() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("../Assets/imageIcon.png");

        if (url != null) {
            return new ImageIcon(url);
        } else {
            return null; // Unable to find the resource
        }
    }
}
