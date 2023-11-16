import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    private JTree tree;

    public MyFrame(String menuEtiq) {
        this.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu memoire = new JMenu("Memoire");
        JMenu recherche = new JMenu("Recherche");
        JMenu enseignant = new JMenu("Enseignants");

        JMenuItem ajouterMenuItem = new JMenuItem("Ajouter");
        JMenuItem lireMenuItem = new JMenuItem("Lire");
        JMenuItem modifierMenuItem = new JMenuItem("Modifier");
        JMenuItem supprimerMenuItem = new JMenuItem("Supprimer");

        // Add ActionListener to menu items
        ajouterMenuItem.addActionListener(new MenuActionListener());
        lireMenuItem.addActionListener(new MenuActionListener());
        modifierMenuItem.addActionListener(new MenuActionListener());
        supprimerMenuItem.addActionListener(new MenuActionListener());

        memoire.add(ajouterMenuItem);
        memoire.add(lireMenuItem);
        memoire.add(modifierMenuItem);
        memoire.add(supprimerMenuItem);

        menuBar.add(memoire);
        menuBar.add(recherche);
        menuBar.add(enseignant);

        add(menuBar, BorderLayout.NORTH);
        // --> Menu Bar

        // Navigation Bar
        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();

        DefaultMutableTreeNode memoireNode = new DefaultMutableTreeNode("Espace memoire");

        DefaultMutableTreeNode createMemoireNode = new DefaultMutableTreeNode("Ajouter");
        DefaultMutableTreeNode readMemoireNode = new DefaultMutableTreeNode("Lire");
        DefaultMutableTreeNode modifyMemoireNode = new DefaultMutableTreeNode("Modifier");
        DefaultMutableTreeNode deleteMemoireNode = new DefaultMutableTreeNode("Supprimer");

        memoireNode.add(createMemoireNode);
        memoireNode.add(readMemoireNode);
        memoireNode.add(modifyMemoireNode);
        memoireNode.add(deleteMemoireNode);

        DefaultMutableTreeNode searchNode = new DefaultMutableTreeNode("Espace recherche");

        DefaultMutableTreeNode profNode = new DefaultMutableTreeNode("Espace Enseignants");

        DefaultMutableTreeNode createProfNode = new DefaultMutableTreeNode("Ajouter");
        DefaultMutableTreeNode modifyProfNode = new DefaultMutableTreeNode("Modifier");
        DefaultMutableTreeNode deleteProfNode = new DefaultMutableTreeNode("Supprimer");

        profNode.add(createProfNode);
        profNode.add(modifyProfNode);
        profNode.add(deleteProfNode);

        parentNode.add(memoireNode);
        parentNode.add(searchNode);
        parentNode.add(profNode);

        tree = new JTree(parentNode);
        add(new JScrollPane(tree), BorderLayout.WEST);
        // --> Navigation Bar

        // Main Space
        JPanel mainPanel = new JPanel();

        mainPanel.setPreferredSize(new Dimension(200, 200));
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Espace principal "));
        this.add(mainPanel, BorderLayout.CENTER);
        // --> Main Space

        // Visualisation Bar
        JPanel visualisationPanel = new JPanel();

        visualisationPanel.setPreferredSize(new Dimension(200, 100));
        visualisationPanel.setBackground(Color.white);
        visualisationPanel.setBorder(BorderFactory.createTitledBorder("Barre visualisation "));
        this.add(visualisationPanel, BorderLayout.EAST);
        // --> Visualisation Bar

        // Bottom Bar
        JPanel bottomBar = new JPanel();

        bottomBar.setPreferredSize(new Dimension(200, 30));
        bottomBar.setBackground(Color.lightGray);
        this.add(bottomBar, BorderLayout.SOUTH);
        // --> Bottom Bar

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) e.getSource();
            String actionCommand = source.getText();

            // Update JTree based on the selected menu item
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
            DefaultMutableTreeNode memoireNode = (DefaultMutableTreeNode) root.getChildAt(0);

            switch (actionCommand) {
                case "Ajouter":
                    // Update for "Ajouter" action
                    break;
                case "Lire":
                    // Update for "Lire" action
                    break;
                case "Modifier":
                    // Update for "Modifier" action
                    break;
                case "Supprimer":
                    // Update for "Supprimer" action
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyFrame("Menu Example");
        });
    }
}
