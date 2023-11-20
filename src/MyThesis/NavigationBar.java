package MyThesis;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class NavigationBar extends DefaultMutableTreeNode {

    NavigationBar() {
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

        DefaultMutableTreeNode searchChild = new DefaultMutableTreeNode("Rechercher");

        searchNode.add(searchChild);

        DefaultMutableTreeNode profNode = new DefaultMutableTreeNode("Espace Enseignants");

        DefaultMutableTreeNode createProfNode = new DefaultMutableTreeNode("Ajouter");
        DefaultMutableTreeNode modifyProfNode = new DefaultMutableTreeNode("Modifier");
        DefaultMutableTreeNode deleteProfNode = new DefaultMutableTreeNode("Supprimer");

        profNode.add(createProfNode);
        profNode.add(modifyProfNode);
        profNode.add(deleteProfNode);

        this.add(memoireNode);
        this.add(searchNode);
        this.add(profNode);
    }
}
