package Components;

import Models.Memoire;

import javax.swing.*;

public class SearchResultPanel extends JPanel {

    SearchResultPanel(Memoire[] memoires) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Memoire memoire : memoires) {
            add(new MemoireCard(memoire));
        }
    }
}
