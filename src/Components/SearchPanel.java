package Components;

import Models.Memoire;
import Models.User;
import MyThesis.MainContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchPanel extends JPanel implements KeyListener {

    User user;
    JTextField textField = new JTextField();

    SearchResultPanel resultPanel;

    public SearchPanel(User user) {
        this.user = user;
        queryGetterBuilder();
    }

    private void queryGetterBuilder() {
        JLabel label = new JLabel("Search for a memoire");
        textField.setPreferredSize(new Dimension(300, 56));

        textField.addKeyListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label);
        add(textField);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        SwingWorker<Memoire[], Void> worker = new SwingWorker<>() {
            @Override
            protected Memoire[] doInBackground() {
                try {
                    return user.getMemoires(textField.getText());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            protected void done() {
                try {
                    Memoire[] memoires = get();

                    if (resultPanel != null) {
                        remove(resultPanel);
                    }
                    resultPanel = new SearchResultPanel(memoires);
                    add(resultPanel);

                    SwingUtilities.invokeLater(() -> {
                        revalidate();
                        repaint();
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
