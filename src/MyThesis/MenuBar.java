package MyThesis;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    MenuBar() {
        JMenu memoire = new JMenu("Models.Models.Memoire");
        JMenu recherche = new JMenu("Recherche");
        JMenu enseignant = new JMenu("Enseignants");

        JMenuItem ajouterMenuItem = new JMenuItem("Ajouter");
        JMenuItem lireMenuItem = new JMenuItem("Lire");
        JMenuItem modifierMenuItem = new JMenuItem("Modifier");
        JMenuItem supprimerMenuItem = new JMenuItem("Supprimer");

        memoire.add(ajouterMenuItem);
        memoire.add(lireMenuItem);
        memoire.add(modifierMenuItem);
        memoire.add(supprimerMenuItem);

        this.add(memoire);
        this.add(recherche);
        this.add(enseignant);
    }
}
