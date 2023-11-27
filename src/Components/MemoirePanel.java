package Components;

import Models.*;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoirePanel extends JPanel implements ActionListener {

    private final User user;
    private final CallBack callBack;
    private final MethodWithMemoire method;
    private InlineField title;
    private InlineField prof;
    private InlineField year;
    private InlineField author1;
    private InlineField author2;
    private InlineField author3;
    private JComboBox<Level> levels;
    private ResumeField resumeField;
    private final JPanel pdfPanel = new JPanel();
    private final JButton choosePdfButton = new JButton("choose pdf");
    private final JButton showPdf = new JButton("show pdf");
    private final JPanel buttonsPanel = new JPanel();
    private final JButton confirm = new JButton("confirm");
    private final JButton cancel = new JButton("cancel");

    private String pdfPath;

    public MemoirePanel(User user, MethodWithMemoire method, CallBack callBack) {
        this.user = user;
        this.callBack = callBack;
        this.method = method;
        setLayout();

        switch (method.method) {
            case CREATE -> setCreateMemoire();
            case READ -> setReadMemoire(method.memoire);
            case UPDATE -> setUpdateMemoire(method.memoire);
            case DELETE -> setDeleteMemoire(method.memoire);
        }

        addComponents();
        setActionListeners();
    }

    void setCreateMemoire() {
        title = new InlineField("Titre: ");
        prof = new InlineField("Encadreur: ");
        year = new InlineField("Annee: ");
        author1 = new InlineField("Auteur 1: ");
        author2 = new InlineField("Auteur 2: ");
        author3 = new InlineField("Auteur 3: ");

        levels = new JComboBox<>(Level.values());

        resumeField = new ResumeField("Resume");
    }

    void setReadMemoire(@NotNull Memoire memoire) {
        boolean disabled = true;
        pdfPath = memoire.pdfUrl;

        title = new InlineField("Titre: ", memoire.title, disabled);
        prof = new InlineField("Encadreur: ", memoire.professor.nom + " " + memoire.professor.prenom, disabled);
        year = new InlineField("Annee: ",  Integer.toString(memoire.date), disabled);

        if (memoire.authors.length >= 1) {
            author1 = new InlineField("Auteur 1: ", memoire.authors[0].nom + " " + memoire.authors[0].prenom, disabled);
        } else {
            author1 = new InlineField("Auteur 1: ", "", disabled);
        }

        if (memoire.authors.length >= 2) {
            author2 = new InlineField("Auteur 2: ",memoire.authors[1].nom + " " + memoire.authors[1].prenom, disabled);
        } else {
            author2 = new InlineField("Auteur 2: ", "", disabled);
        }

        if (memoire.authors.length >= 3) {
            author3 = new InlineField("Auteur 3: ", memoire.authors[2].nom + " " + memoire.authors[2].prenom, disabled);
        } else {
            author3 = new InlineField("Auteur 3: ", "", disabled);
        }

        levels = new JComboBox<>(Level.values());
        levels.setSelectedItem(memoire.level);
        levels.setEnabled(!disabled);

        resumeField = new ResumeField("Resume", memoire.resume, disabled);
        choosePdfButton.setEnabled(false);
    }

    void setUpdateMemoire(@NotNull Memoire memoire) {
        boolean disabled = false;
        pdfPath = memoire.pdfUrl;

        title = new InlineField("Titre: ", memoire.title, disabled);
        if(memoire.professor != null) {
            prof = new InlineField("Encadreur: ", memoire.professor.id, memoire.professor.nom + " " + memoire.professor.prenom, disabled);
        } else {
            prof = new InlineField("Encadreur: ", null, "Deleted Professor", disabled);
        }
        year = new InlineField("Annee: ",  Integer.toString(memoire.date), disabled);

        if (memoire.authors.length >= 1) {
            author1 = new InlineField("Auteur 1: ", memoire.authors[0].id, memoire.authors[0].nom + " " + memoire.authors[0].prenom, disabled);
        } else {
            author1 = new InlineField("Auteur 1: ", "", disabled);
        }

        if (memoire.authors.length >= 2) {
            author2 = new InlineField("Auteur 2: ", memoire.authors[1].id, memoire.authors[1].nom + " " + memoire.authors[1].prenom, disabled);
        } else {
            author2 = new InlineField("Auteur 2: ", "", disabled);
        }

        if (memoire.authors.length >= 3) {
            author3 = new InlineField("Auteur 3: ", memoire.authors[2].id, memoire.authors[2].nom + " " + memoire.authors[2].prenom, disabled);
        } else {
            author3 = new InlineField("Auteur 3: ", "", disabled);
        }

        levels = new JComboBox<>(Level.values());
        levels.setSelectedItem(memoire.level);
        levels.setEnabled(!disabled);

        resumeField = new ResumeField("Resume", memoire.resume, disabled);
    }

    void setDeleteMemoire(@NotNull Memoire memoire) {
        boolean disabled = true;
        pdfPath = memoire.pdfUrl;

        title = new InlineField("Titre: ", memoire.title, disabled);
        if(memoire.professor != null) {
            prof = new InlineField("Encadreur: ", memoire.professor.nom + " " + memoire.professor.prenom, disabled);
        } else {
            prof = new InlineField("Encadreur: ", null, "Deleted Professor", disabled);
        }
        year = new InlineField("Annee: ",  Integer.toString(memoire.date), disabled);

        if (memoire.authors.length >= 1) {
            author1 = new InlineField("Auteur 1: ", memoire.authors[0].nom + " " + memoire.authors[0].prenom, disabled);
        } else {
            author1 = new InlineField("Auteur 1: ", "", disabled);
        }

        if (memoire.authors.length >= 2) {
            author2 = new InlineField("Auteur 2: ", memoire.authors[1].nom + " " + memoire.authors[1].prenom, disabled);
        } else {
            author2 = new InlineField("Auteur 2: ", "", disabled);
        }

        if (memoire.authors.length >= 3) {
            author3 = new InlineField("Auteur 3: ",memoire.authors[2].nom + " " + memoire.authors[2].prenom, disabled);
        } else {
            author3 = new InlineField("Auteur 3: ", "", disabled);
        }

        levels = new JComboBox<>(Level.values());
        levels.setSelectedItem(memoire.level);
        levels.setEnabled(!disabled);

        resumeField = new ResumeField("Resume", memoire.resume, disabled);

        choosePdfButton.setEnabled(false);
    }

    private void setLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        pdfPanel.setLayout(new FlowLayout());
    }

    private void addComponents() {
        this.add(title);
        this.add(prof);
        this.add(year);
        this.add(author1);
        this.add(author2);
        this.add(author3);
        this.add(levels);
        this.add(resumeField);

        pdfPanel.add(choosePdfButton);
        pdfPanel.add(showPdf);

        buttonsPanel.add(cancel);
        buttonsPanel.add(confirm);

        this.add(pdfPanel);
        this.add(buttonsPanel);
    }

    private void setActionListeners() {
        choosePdfButton.addActionListener(this);
        showPdf.addActionListener(this);
        confirm.addActionListener(this);
        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == confirm) {
            if(user instanceof Admin) {
                if(method.method == Method.DELETE) {
                    try {
                        ((Admin) user).deleteMemoire(method.memoire.cote);

                        this.callBack.onSuccess();
                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(MemoirePanel.this, error.getMessage());
                        this.callBack.onFailure();
                    }
                }

                String title = this.title.getText();
                Long professorId = Long.valueOf(this.prof.getText());
                int date = Integer.parseInt(this.year.getText());

                Level level = (Level) this.levels.getSelectedItem();
                String resume = this.resumeField.getText();

                try {
                    if(title.isEmpty() || resume.isEmpty()) {
                        JOptionPane.showMessageDialog(MemoirePanel.this, "please fill all required fields");
                        return;
                    }

                    if(method.method == Method.CREATE) {
                        List<Long> studentsIds = new ArrayList<>();

                        if (!this.author1.getText().isEmpty()) {
                            studentsIds.add(Long.valueOf(this.author1.getText()));
                        }
                        if (!this.author2.getText().isEmpty()) {
                            studentsIds.add(Long.valueOf(this.author2.getText()));
                        }
                        if (!this.author3.getText().isEmpty()) {
                            studentsIds.add(Long.valueOf(this.author3.getText()));
                        }

                        Long[] studentsIdsArray = studentsIds.toArray(new Long[0]);

                        ((Admin) user).createMemoire(title, professorId, date, studentsIdsArray, level, resume, pdfPath);
                    } else if(method.method == Method.UPDATE) {
                        ConnectionUrlParser.Pair<Long, Long> studentId1 = new ConnectionUrlParser.Pair<>(method.memoire.authors[0].id, Long.valueOf(author1.getText()));
                        ConnectionUrlParser.Pair<Long, Long> studentId2 = null;
                        ConnectionUrlParser.Pair<Long, Long> studentId3 = null;

                        if(!author2.getText().isEmpty()) {
                            if (method.memoire.authors.length >= 2) {
                                studentId2 = new ConnectionUrlParser.Pair<>(method.memoire.authors[1].id, Long.valueOf(author2.getText()));
                            } else {
                                studentId2 = new ConnectionUrlParser.Pair<>(null, Long.valueOf(author2.getText()));
                            }
                        }
                        if(!author3.getText().isEmpty()) {
                            if (method.memoire.authors.length >= 3) {
                                studentId3 = new ConnectionUrlParser.Pair<>(method.memoire.authors[2].id, Long.valueOf(author3.getText()));
                            } else {
                                studentId3 = new ConnectionUrlParser.Pair<>(null, Long.valueOf(author3.getText()));
                            }
                        }

                        ((Admin) user).updateMemoire(method.memoire.cote, title, professorId, date, studentId1, studentId2, studentId3, level, resume, pdfPath);
                    }

                    this.callBack.onSuccess();

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(MemoirePanel.this, error.getMessage());
                    this.callBack.onFailure();
                }
            }
        } else if(e.getSource() == cancel) {
            this.callBack.onCancel();
        } else if(e.getSource() == choosePdfButton) {
            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(MemoirePanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {

                java.io.File selectedFile = fileChooser.getSelectedFile();

                pdfPath = selectedFile.getAbsolutePath();
            }
        } else if(e.getSource() == showPdf) {
            if (!pdfPath.isEmpty()) {
                openPdf(pdfPath);
            } else {
                JOptionPane.showMessageDialog(MemoirePanel.this, "Please choose a PDF file first.");
            }
        }
    }

    private void openPdf(String pdfPath) {
        try {
            Desktop.getDesktop().open(new File(pdfPath));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MemoirePanel.this, e.getMessage());
        }
    }

    public interface CallBack {
        void onSuccess();
        void onFailure();
        void onCancel();
    }
}
