package MyThesis;

import Components.InlineField;
import Components.MemoirePanel;
import Components.SearchPanel;
import Models.Memoire;
import Models.User;

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
        reinit( () -> {

            this.setBorder(BorderFactory.createTitledBorder(selectedNode.toString()));

            switch (selectedNode.toString()) {
                case "Ajouter" -> reinit(() -> {
                    MemoirePanel createMemoire = new MemoirePanel(user, new MemoirePanel.CallBack() {
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
                case "Lire" -> {
                    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    InlineField readField = new InlineField("Id de memoire a lire: ");
                    JButton getIdReadButton = new JButton("confirmer");
                    getIdReadButton.addActionListener(e -> {
                        Memoire memoire = null;
                        try {
                            memoire = user.readMemoire(Integer.parseInt(readField.getText()));
                            Memoire finalMemoire = memoire;
                            reinit(() -> {
                                MemoirePanel readMemoire = new MemoirePanel(user, finalMemoire, true, new MemoirePanel.CallBack() {
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
                            });
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    this.add(readField);
                    this.add(getIdReadButton);
                }
                case "Modifier" -> {
                    InlineField modifyField = new InlineField("Id de memoire a modifier: ");
                    JButton getIdModifyButton = new JButton("confirmer");
                    getIdModifyButton.addActionListener(e -> {
                        Memoire memoire = null;
                        try {
                            memoire = user.readMemoire(Integer.parseInt(modifyField.getText()));
                            Memoire finalMemoire = memoire;
                            reinit(() -> {
                                MemoirePanel modifyMemoire = new MemoirePanel(user, finalMemoire, false, new MemoirePanel.CallBack() {
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
                            });
                        } catch (Exception error) {
                            JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
                        }
                    });
                    this.add(modifyField);
                    this.add(getIdModifyButton);
                    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                }
                case "Supprimer" -> {
                    InlineField deleteField = new InlineField("Id de memoire a supprimer: ");
                    JButton getIdDeleteButton = new JButton("confirmer");
                    getIdDeleteButton.addActionListener(e -> {
                        Memoire memoire = null;
                        try {
                            memoire = user.readMemoire(Integer.parseInt(deleteField.getText()));
                            Memoire finalMemoire = memoire;
                            reinit(() -> {
                                MemoirePanel deleteMemoire = new MemoirePanel(user, finalMemoire, true, new MemoirePanel.CallBack() {
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

                                this.add(deleteMemoire);
                            });
                        } catch (Exception error) {
                            JOptionPane.showMessageDialog(this, error.getLocalizedMessage());
                        }
                    });
                    this.add(deleteField);
                    this.add(getIdDeleteButton);
                    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                }
                case "Rechercher" -> {
                    add(new SearchPanel(user));
                }
                default -> {
                }
            }
        });
    }

    void reinit(BuildCallBack callBack) {
        this.removeAll();

        callBack.newBuild();
        // Revalidate and repaint the main panel
        this.revalidate();
        this.repaint();
    }

    interface BuildCallBack {
        void newBuild();
    }
}
