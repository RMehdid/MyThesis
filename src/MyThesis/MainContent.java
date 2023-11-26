package MyThesis;

import Components.InlineField;
import Components.MemoirePanel;
import Components.ProfessorPanel;
import Components.SearchPanel;
import Models.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainContent extends JPanel {

    User user;

    MainContent(User user) {
        this.user = user;
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createTitledBorder("Espace principal "));
    }
    //hey samy

    void buildContent(DefaultMutableTreeNode selectedNode) {
        reinit(() -> {

            this.setBorder(BorderFactory.createTitledBorder(selectedNode.toString()));

            switch (selectedNode.toString()) {
                case "Ajouter" -> reinit(() -> {
                    MemoirePanel createMemoire = new MemoirePanel(user, new MethodWithMemoire(Method.CREATE, null), new MemoirePanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }
                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }
                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });
                    this.add(createMemoire);
                });
                case "Lire" -> getMemoirePanel(memoire -> reinit(() -> {
                    MemoirePanel readMemoire = new MemoirePanel(user, new MethodWithMemoire(Method.READ, memoire), new MemoirePanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });

                    this.add(readMemoire);
                }));
                case "Modifier" -> getMemoirePanel(memoire -> reinit(() -> {
                        MemoirePanel modifyMemoire = new MemoirePanel(user, new MethodWithMemoire(Method.UPDATE, memoire), new MemoirePanel.CallBack() {
                            @Override
                            public void onSuccess() {
                                buildContent(selectedNode);
                            }

                            @Override
                            public void onFailure() {
                                buildContent(selectedNode);
                            }

                            @Override
                            public void onCancel() {
                                buildContent(selectedNode);
                            }
                        });

                        this.add(modifyMemoire);
                }));
                case "Supprimer" -> getMemoirePanel(memoire -> reinit(() -> {
                    MemoirePanel readMemoire = new MemoirePanel(user, new MethodWithMemoire(Method.DELETE, memoire), new MemoirePanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });

                    this.add(readMemoire);
                }));
                case "Rechercher" -> {
                    add(new SearchPanel(user));
                }
                case "Ajouter Enseignant" -> {
                    ProfessorPanel createProfessor = new ProfessorPanel(user, new MethodWithProfessor(Method.CREATE, null), new ProfessorPanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });
                    this.add(createProfessor);
                }
                case "Modifier Enseignant" -> getProfessorPanel(professor -> reinit(() -> {
                    ProfessorPanel modifyProfessor = new ProfessorPanel(user, new MethodWithProfessor(Method.UPDATE, professor), new ProfessorPanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });

                    this.add(modifyProfessor);
                }));
                case "Supprimer Enseignant" -> getProfessorPanel(professor -> reinit(() -> {
                    ProfessorPanel deleteProfessor = new ProfessorPanel(user, new MethodWithProfessor(Method.DELETE, professor), new ProfessorPanel.CallBack() {
                        @Override
                        public void onSuccess() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onFailure() {
                            buildContent(selectedNode);
                        }

                        @Override
                        public void onCancel() {
                            buildContent(selectedNode);
                        }
                    });

                    this.add(deleteProfessor);
                }));
            }
        });
    }

    void getMemoirePanel(IdMemoireCallBack callBack) {
        InlineField idField = new InlineField("Memoire id: ");
        JButton getIdButton = new JButton("confirm");
        getIdButton.addActionListener(e -> {
            try{
                Memoire memoire = user.readMemoire(Integer.parseInt(idField.getText()));
                if(memoire != null) {
                    callBack.next(memoire);
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        });

        this.add(idField);
        this.add(getIdButton);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    void getProfessorPanel(IdProfessorCallBack callBack) {
        InlineField idField = new InlineField("Professor id: ");
        JButton getIdButton = new JButton("confirm");
        getIdButton.addActionListener(e -> {
            try{
                Professor professor = ((Admin) user).getProfessor(Long.valueOf(idField.getText()));
                if(professor != null) {
                    callBack.next(professor);
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
            }
        });

        this.add(idField);
        this.add(getIdButton);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    void reinit(@NotNull BuildCallBack callBack) {
        this.removeAll();

        callBack.newBuild();
        // Revalidate and repaint the main panel
        this.revalidate();
        this.repaint();
    }

    interface BuildCallBack {
        void newBuild();
    }

    interface IdMemoireCallBack {
        void next(Memoire memoire);
    }

    interface IdProfessorCallBack {
        void next(Professor professor);
    }
}
