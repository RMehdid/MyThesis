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

    JPanel upperBar;
    NavigationBar navBar;
    private JTree tree;
    private MainContent mainContent;
    ProfileBar profileBar;
    JPanel bottomBar;

    public MyThesis(User user) {
        setLayout();
        initializeComponents(user);
        setPreferredSize();
        setBackgroundColor();
        addActionListeners();
        addComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setLayout() {
        setLayout(new BorderLayout());
    }
    private void initializeComponents(User user) {
        upperBar = new JPanel();
        navBar = new NavigationBar(user);
        tree = new JTree(navBar);
        mainContent = new MainContent(user);
        profileBar = new ProfileBar(user);
        bottomBar = new JPanel();
    }
    private void setPreferredSize() {
        upperBar.setPreferredSize(new Dimension(200, 30));
        bottomBar.setPreferredSize(new Dimension(200, 30));
        setSize(900, 600);
    }
    private void setBackgroundColor() {
        upperBar.setBackground(Color.lightGray);
        bottomBar.setBackground(Color.lightGray);
    }
    private void addActionListeners() {
        tree.addTreeSelectionListener(this);
    }
    private void addComponents() {
        this.add(upperBar, BorderLayout.NORTH);
        this.add(tree, BorderLayout.WEST);
        this.add(mainContent, BorderLayout.CENTER);
        this.add(profileBar, BorderLayout.EAST);
        this.add(bottomBar, BorderLayout.SOUTH);
    }
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            mainContent.buildContent(selectedNode);
        }
    }
}
