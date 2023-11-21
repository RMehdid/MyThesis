package Components;

import Models.Memoire;
import Models.User;
import MyThesis.MainContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchPanel extends JPanel implements KeyListener {

    JTextField textField = new JTextField();

    public SearchPanel(User user) {
        idGetterBuilder();
    }

    private void idGetterBuilder() {
        JLabel label = new JLabel("Search for a memoire");
        textField.setPreferredSize(new Dimension(300, 56));

        textField.addKeyListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label);
        add(textField);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Memoire[] memoires = new Memoire[]{Memoire.memoireExm};
        for (Memoire memoire : memoires) {
            this.removeAll();
            idGetterBuilder();
            add(new MemoireCard(memoire));
            revalidate();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
