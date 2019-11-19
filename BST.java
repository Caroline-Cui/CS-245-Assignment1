//Binary search tree implementation. I had three function for binary search tree. (similar with trie)
//I had another function which help to balance the tree
//This will make the running time decrease from O(n) to O(log(n))
import static java.lang.Math.*;

public class BST implements Structure
{
    BSTNode root;

    public BST()
    {
        root = null;
    }
//find if the node is in the tree
    @Override
    public boolean find(String item)
    {
        return findNode(root, item);
    }

    private boolean findNode(BSTNode node, String item)
    {
        if (node == null)
        {
            return false;
        }
        if (item.compareTo(node.data) == 0)
        {
            return true;
        }
        if (item.compareTo(node.data) > 0)
        {
            return findNode(node.right, item);
        }
        else
        {
            return findNode(node.left, item);
        }
    }
//insert Node. Check if balance during insertion 
    @Override
    public void insertNode(String item)
    {
        root = insertNode(item, root);
    }

    private BSTNode insertNode(String item, BSTNode node)
    {
        if (node == null)
        {
            node = new BSTNode(item);
            return node;
        }
        if (item.compareTo(node.data) < 0)
        {
            node.left = insertNode(item, node.left);
            int lH = height(node.left);
            int rH = height(node.right);
            if (lH - rH == 2)
            {
                if (item.compareTo(node.left.data)<0)
                {
                    node = balanceLeft(node);
                }
                else
                {
                    node = doubleBalanceLeft(node);
                }
            }
//            node.left = insertNode(item, node.left);
        }
        else
        {
            node.right = insertNode(item, node.right);
            int lH = height(node.left);
            int rH = height(node.right);
            if (rH - lH == 2)
            {
                if (item.compareTo(node.right.data)>0)
                {
                    node = balanceRight(node);
                }

                else
                {
                    node = doubleBalanceRight(node);
                }
            }
//            node.right = insertNode(item, node.right);
        }
        return node;
    }

    private BSTNode doubleBalanceLeft(BSTNode node)
    {
        node.left = balanceRight(node.left);
        return balanceLeft(node);
    }

    private BSTNode doubleBalanceRight(BSTNode node)
    {
        node.right = balanceLeft(node.right);
        return balanceRight(node);
    }

    private BSTNode balanceLeft(BSTNode node)
    {
        BSTNode temp = node.left;
        node.left = node.left.right;
        temp.right = node;
        return temp;
    }

    private BSTNode balanceRight(BSTNode node)
    {
        BSTNode temp = node.right;
        node.right = node.right.left;
        temp.left = node;
        return temp;
    }

    private int height(BSTNode node)
    {
        if (node == null)
        {
            return 0;
        }
        return 1 + max(height(node.left), height(node.right));
    }
//not the "good suggestion", but it will still give three suggestion when 
//the word is not in the english file
    @Override
    public String[] suggest(String item)
    {
        String[] result = new String[3];
        result[0] = suggest(item, root);
        if (item.compareTo(root.data)>0) {
            result[1] = suggest(item, root.left);
        }
        else {
            result[1] = suggest(item, root.right);
        }
        if (item.compareTo(root.left.data)>0) {
            result[2] = suggest(item, root.left.left);
        }
        else{
            result[2] = suggest(item, root.left.right);
        }
        return result;
    }

    private String suggest(String item, BSTNode node)
    {
        if (node.left == null || node.right == null) {
            return node.data;
        }
        if (node.data.compareTo(item) > 0)
            return suggest(item, node.left);
        else
            return suggest(item, node.right);

    }
}
