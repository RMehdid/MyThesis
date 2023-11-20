package MyThesis;

import Models.Admin;
import Models.Student;
import Models.User;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MyThesis extends JFrame implements TreeSelectionListener {

    private JTree tree;
    private MainContent mainContent;

    public MyThesis(User user) {
        this.setLayout(new BorderLayout());

        // Navigation Bar
        NavigationBar navBar = new NavigationBar();

        tree = new JTree(navBar);
        tree.addTreeSelectionListener(this);

        add(tree, BorderLayout.WEST);
        // --> Navigation Bar

        // Main Space
        mainContent = new MainContent();

        this.add(mainContent, BorderLayout.CENTER);
        // --> Main Space

        if(user instanceof Admin) {

            // Visualisation Bar
            VisualizationBar visualBar = new VisualizationBar();

            this.add(visualBar, BorderLayout.EAST);
            // --> Visualisation Bar
        }

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

    private void initializeComponents() {
        tree = new JTree();
    }

    public static void main(String[] args) {
        MyThesis frame = new MyThesis(Admin.admin);

        frame.setVisible(true);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            mainContent.buildContent(selectedNode);
        }
    }
}
