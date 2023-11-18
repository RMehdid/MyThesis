import Components.LabeledField;
import Components.MemoireCard;
import Components.MemoirePanel;
import Models.Memoire;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MainContent extends JPanel {

    MainContent() {
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createTitledBorder("Espace principal "));
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
                    LabeledField readField = new LabeledField("Id de memoire a lire: ");
                    JButton getIdReadButton = new JButton("confirmer");
                    getIdReadButton.addActionListener(e -> {
                        getMemoire(readField.getText(), memoire -> {
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
                    });
                    this.add(readField);
                    this.add(getIdReadButton);
                    this.setLayout(new GridLayout(2, 1));
                }
                case "Modifier" -> {
                    LabeledField modifyField = new LabeledField("Id de memoire a modifier: ");
                    JButton getIdModifyButton = new JButton("confirmer");
                    getIdModifyButton.addActionListener(e -> {
                        getMemoire(modifyField.getText(), memoire -> {
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
                    });
                    this.add(modifyField);
                    this.add(getIdModifyButton);
                    this.setLayout(new GridLayout(2, 1));
                }
                case "Supprimer" -> {
                    LabeledField deleteField = new LabeledField("Id de memoire a supprimer: ");
                    JButton getIdDeleteButton = new JButton("confirmer");
                    getIdDeleteButton.addActionListener(e -> {
                        getMemoire(deleteField.getText(), memoire -> {
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
                 getMemoires(query.getText(), new SearchCallBack() {
                     @Override
                     public void onSuccess(Memoire[] memoires) {
                         reinit(() -> {
                             setLayout(new BoxLayout(MainContent.this, BoxLayout.Y_AXIS));
                             for (Memoire memoire : memoires) {
                                 add(new MemoireCard(memoire));
                             }
                         });
                     }

                     @Override
                     public void onFailure() {

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

    void getMemoire(String id, Memoire.MemoireCallback callback) {
        //TODO: api call get memoire
        Memoire.getMemoire(id, callback);
    }

    void getMemoires(String query, SearchCallBack callback) {
        //TODO: api call get memoire
        Memoire.getMemoire(query, memoire -> {
            Memoire[] memoires = {memoire, memoire, memoire};
            callback.onSuccess(memoires);
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

    interface SearchCallBack {
        void onSuccess(Memoire[] memoires);
        void onFailure();
    }
}
