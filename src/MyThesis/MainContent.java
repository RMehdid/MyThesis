package MyThesis;

import Components.InlineField;
import Components.MemoireCard;
import Components.MemoirePanel;
import Models.Memoire;
import Models.Speciality;
import Models.Student;
import Models.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainContent extends JPanel {

    User user;

    MainContent() {
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createTitledBorder("Espace principal "));

        user = new Student(342L, "Mehdid", "Samy Abderraouf",Speciality.Informatics);
    }
    //hey samy

    void buildContent(DefaultMutableTreeNode selectedNode) {
        reinit( () -> {

            this.setBorder(BorderFactory.createTitledBorder(selectedNode.toString()));

            switch (selectedNode.toString()) {
                case "Ajouter" -> {
                    MemoirePanel createMemoire = new MemoirePanel(new MemoirePanel.CallBack() {
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
                }
                case "Lire" -> {
                    InlineField readField = new InlineField("Id de memoire a lire: ");
                    JButton getIdReadButton = new JButton("confirmer");
                    getIdReadButton.addActionListener(e -> {
                        Memoire memoire = user.readMemoire(Integer.parseInt(readField.getText()));

                        reinit(() -> {
                            MemoirePanel readMemoire = new MemoirePanel(memoire, true, new MemoirePanel.CallBack() {
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
                    });
                    this.add(readField);
                    this.add(getIdReadButton);
                    this.setLayout(new GridLayout(2, 1));
                }
                case "Modifier" -> {
                    InlineField modifyField = new InlineField("Id de memoire a modifier: ");
                    JButton getIdModifyButton = new JButton("confirmer");
                    getIdModifyButton.addActionListener(e -> {
                        Memoire memoire = user.readMemoire(Integer.parseInt(modifyField.getText()));

                        reinit(() -> {
                            MemoirePanel modifyMemoire = new MemoirePanel(memoire, false, new MemoirePanel.CallBack() {
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
                    });
                    this.add(modifyField);
                    this.add(getIdModifyButton);
                    this.setLayout(new GridLayout(2, 1));
                }
                case "Supprimer" -> {
                    InlineField deleteField = new InlineField("Id de memoire a supprimer: ");
                    JButton getIdDeleteButton = new JButton("confirmer");
                    getIdDeleteButton.addActionListener(e -> {
                        Memoire memoire = user.readMemoire(Integer.parseInt(deleteField.getText()));

                        reinit(() -> {
                            MemoirePanel deleteMemoire = new MemoirePanel(memoire, true, new MemoirePanel.CallBack() {
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
                    });
                    this.add(deleteField);
                    this.add(getIdDeleteButton);
                    this.setLayout(new GridLayout(2, 1));
                }
                case "Rechercher" -> {
                    searchMemoireBuild();
                }
                default -> {
                }
            }
        });
    }

     void searchMemoireBuild() {
         JTextField query = new JTextField(10);

         query.addKeyListener(new KeyListener() {
             @Override
             public void keyTyped(KeyEvent e) {
                 // TODO: - Perform search call
                 System.out.println("search for: " + query.getText());
                 Memoire[] memoires = user.getMemoires(query.getText());

                 reinit(() -> {
                     setLayout(new BoxLayout(MainContent.this, BoxLayout.Y_AXIS));
                     for (Memoire memoire : memoires) {
                         add(new MemoireCard(memoire));
                     }
                 });
             }

             @Override
             public void keyPressed(KeyEvent e) {
                 //
             }

             @Override
             public void keyReleased(KeyEvent e) {
                 //
             }
         });

         this.add(query);
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
