import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MyFrame extends JFrame {

    private JTree tree;
    private MainContent mainContent;

    public MyFrame() {
        this.setLayout(new BorderLayout());

        // Menu Bar
        MenuBar menuBar = new MenuBar();

        add(menuBar, BorderLayout.NORTH);
        // --> Menu Bar

        // Navigation Bar
        NavigationBar navBar = new NavigationBar();

        tree = new JTree(navBar);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null) {
                    mainContent.buildContent(selectedNode);
                }
            }
        });

        add(new JScrollPane(tree), BorderLayout.WEST);
        // --> Navigation Bar

        // Main Space
        mainContent = new MainContent();

        this.add(mainContent, BorderLayout.CENTER);
        // --> Main Space

        // Visualisation Bar
        VisualizationBar visualBar = new VisualizationBar();

        this.add(visualBar, BorderLayout.EAST);
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

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();

        frame.setVisible(true);
    }
}
