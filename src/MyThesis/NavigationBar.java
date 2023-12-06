package MyThesis;

import Models.Admin;
import Models.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class NavigationBar extends DefaultMutableTreeNode {

    DefaultMutableTreeNode memoireNode;
    DefaultMutableTreeNode createMemoireNode;
    DefaultMutableTreeNode modifyMemoireNode;
    DefaultMutableTreeNode readMemoireNode;
    DefaultMutableTreeNode deleteMemoireNode;
    DefaultMutableTreeNode searchNode;
    DefaultMutableTreeNode searchChild;
    DefaultMutableTreeNode profNode;
    DefaultMutableTreeNode createProfNode;
    DefaultMutableTreeNode modifyProfNode;
    DefaultMutableTreeNode deleteProfNode;

    NavigationBar(User user) {
        initializeComponents();
        addComponents(user);
    }

    private void initializeComponents() {
        memoireNode = new DefaultMutableTreeNode("Espace memoire");

        createMemoireNode = new DefaultMutableTreeNode("Ajouter");
        readMemoireNode = new DefaultMutableTreeNode("Lire");
        modifyMemoireNode = new DefaultMutableTreeNode("Modifier");
        deleteMemoireNode = new DefaultMutableTreeNode("Supprimer");

        searchNode = new DefaultMutableTreeNode("Espace recherche");

        searchChild = new DefaultMutableTreeNode("Rechercher");

        profNode = new DefaultMutableTreeNode("Espace Enseignants");

        createProfNode = new DefaultMutableTreeNode("Ajouter Enseignant");
        modifyProfNode = new DefaultMutableTreeNode("Modifier Enseignant");
        deleteProfNode = new DefaultMutableTreeNode("Supprimer Enseignant");
    }
    private void addComponents(User user) {
        memoireNode.add(readMemoireNode);
        if(user instanceof Admin) {
            memoireNode.add(createMemoireNode);
            memoireNode.add(modifyMemoireNode);
            memoireNode.add(deleteMemoireNode);
        }

        searchNode.add(searchChild);

        this.add(memoireNode);
        this.add(searchNode);

        if(user instanceof Admin) {
            profNode.add(createProfNode);
            profNode.add(modifyProfNode);
            profNode.add(deleteProfNode);

            this.add(profNode);
        }
    }
}
