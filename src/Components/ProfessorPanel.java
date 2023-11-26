package Components;

import Auth.Components.InlinePassword;
import Auth.Components.LabeledComboBox;
import Models.*;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProfessorPanel extends JPanel implements ActionListener {

    private final User user;
    private final CallBack callBack;
    private final MethodWithProfessor method;

    InlineField matriculePanel;
    InlineField familyNamePanel;
    InlineField firstnamePanel;
    LabeledComboBox<Speciality> specialityField;
    InlinePassword passwordPanel = new InlinePassword("PASSWORD");
    InlinePassword rePasswordPanel = new InlinePassword("RE-ENTER PASSWORD");
    private final JPanel buttonsPanel = new JPanel();
    JButton primaryButton;
    JButton cancelButton = new JButton("CANCEL");

    public ProfessorPanel(User user, MethodWithProfessor method, CallBack callBack) {
        this.user = user;
        this.callBack = callBack;
        this.method = method;
        setLayout();

        switch (method.method) {
            case CREATE -> setCreateProfessor();
            case UPDATE -> setUpdateProfessor(method.professor);
            case DELETE -> setDeleteProfessor(method.professor);
        }

        addComponents();
        setActionListeners();
    }

    void setCreateProfessor() {
        matriculePanel = new InlineField("Matricule: ");
        familyNamePanel = new InlineField("Nom: ");
        firstnamePanel = new InlineField("Prenom: ");
        specialityField = new LabeledComboBox<>("SPECIALITY", Speciality.values());
        passwordPanel = new InlinePassword("PASSWORD");
        rePasswordPanel = new InlinePassword("RE-ENTER PASSWORD");
        primaryButton = new JButton("CREATE");
    }

    void setUpdateProfessor(Professor professor) {
        boolean disabled = false;

        matriculePanel = new InlineField("Matricule: ", professor.id.toString(), true);
        familyNamePanel = new InlineField("Nom: ", professor.nom, disabled);
        firstnamePanel = new InlineField("Prenom: ", professor.prenom, disabled);
        specialityField = new LabeledComboBox<>("SPECIALITY", Speciality.values());
        specialityField.setSelectedItem(professor.speciality);
        specialityField.setEnabled(!disabled);

        primaryButton = new JButton("UPDATE");
    }

    void setDeleteProfessor(Professor professor) {
        boolean disabled = true;

        matriculePanel = new InlineField("Matricule: ", professor.id.toString(), disabled);

        familyNamePanel = new InlineField("Nom: ", professor.nom, disabled);
        firstnamePanel = new InlineField("Prenom: ", professor.prenom, disabled);
        specialityField = new LabeledComboBox<>("SPECIALITY", Speciality.values());
        specialityField.setSelectedItem(professor.speciality);
        specialityField.setEnabled(!disabled);

        primaryButton = new JButton("DELETE");
    }

    private void setLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
    }

    private void addComponents() {
        this.add(matriculePanel);
        this.add(familyNamePanel);
        this.add(firstnamePanel);
        this.add(specialityField);

        if(method.method == Method.CREATE) {
            this.add(passwordPanel);
            this.add(rePasswordPanel);
        }

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(primaryButton);

        this.add(buttonsPanel);
    }

    private void setActionListeners() {
        primaryButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == primaryButton) {
            if(user instanceof Admin) {
                if(method.method == Method.DELETE) {
                    try {
                        ((Admin) user).deleteProfessor(method.professor.id);

                        this.callBack.onSuccess();
                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(ProfessorPanel.this, error.getMessage());
                        this.callBack.onFailure();
                    }
                }

                Long id = Long.valueOf(this.matriculePanel.getText());
                String nom = this.familyNamePanel.getText();
                String prenom = this.firstnamePanel.getText();
                Speciality speciality = this.specialityField.getSelectedItem();


                try {
                    if(nom.isEmpty() || prenom.isEmpty()) {
                        throw new Exception("please fill all required fields");
                    }

                    if(method.method == Method.CREATE) {

                        String password = passwordPanel.getText();
                        String rePassword = rePasswordPanel.getText();

                        if (rePassword.equals(password)) {
                            try {
                                ((Admin) user).createProfessor(id, nom, prenom, speciality, password);
                            } catch(Exception error) {
                                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Passwords do not match");
                        }

                    } else if(method.method == Method.UPDATE) {

                        ((Admin) user).updateProfessor(method.professor.id, nom, prenom, speciality);
                    }

                    this.callBack.onSuccess();

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(ProfessorPanel.this, error.getMessage());
                    this.callBack.onFailure();
                }
            }
        } else if(e.getSource() == cancelButton) {
            this.callBack.onCancel();
        }
    }

    public interface CallBack {
        void onSuccess();
        void onFailure();
        void onCancel();
    }
}

