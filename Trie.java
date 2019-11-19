//this class I provide ties implementation with find, insert, and suggest method. 
import java.util.Arrays;

public class Trie implements Structure
{
    TrieNode root; // the root node
    String[] arrSuggest; // the array for suggestion
    String str;
    int count, mLD; // count: the number count for elements in arrSuggest
    public Trie()
    {
        root = new TrieNode();
//        arr = new String[3];
        str = "";
    }

    /*
    I had two version for find function one is for public to see the other one is the acutal implementation
    find function is to find target node by calling the fuction recursively

    */
    @Override
    public boolean find(String item)
    {
        char[] arr = item.toCharArray();
        boolean isFind = find(arr,root);
        return isFind;
    }

    private boolean find(char[] item, TrieNode node)
    {
        if (node == null)
        {
            return false;
        }
        if (item.length == 0)
        {
            return node.isWord;
        }
        char ch = item[0];
        alphabet al = new alphabet(ch);
        return find(Arrays.copyOfRange(item, 1, item.length), node.pointer[al.value]);
    }


// insertNode function is to add node into the trie
    @Override
    public void insertNode(String item)
    {
        char[] arr = item.toCharArray();
        root = insertNode(arr, root);
    }

    private TrieNode insertNode(char[] item, TrieNode node)
    {
        if (node == null)
        {
            node = new TrieNode();
        }
        if (item.length == 0)
        {
            node.isWord = true;
            return node;
        }
        char ch = item[0];
        alphabet al = new alphabet(ch);
        node.pointer[al.value] = insertNode(Arrays.copyOfRange(item, 1, item.length), node.pointer[al.value]);
        return node;
    }
// use Levenshtein Distance to check the smallest distance 
// between two words and give the suggestion if the original word is wrong
    @Override
    public String[] suggest(String item)
    {
        arrSuggest = new String[3];
        count = 0;
        mLD = 1000;
        int[] row = new int[item.length()+1];
        for (int i = 0; i < item.length()+1; i++)
            row[i] = i;
        for (int i = 0; i < 27; i++)
        {
            if (root.pointer[i] != null)
                suggest(item, root.pointer[i], (char)(i+'a'), "", row);
        }
        return arrSuggest;
    }

    private void suggest(String item, TrieNode node, char ch, String str, int[] privR)
    {
        int r = 0,a = 0,d = 0, size = privR.length;
        int[] currentR = new int[size];
        currentR[0] = privR[0] + 1;
        int minE = currentR[0];
        for (int i = 1; i < item.length()+1; i++)
        {
            currentR[i] = countRow(ch, item, i, currentR, privR, r, a, d);
            if (currentR[i] < minE)
                minE = currentR[i];
        }
        addItem(currentR[size - 1], node, str, ch);
        if (minE < mLD)
            for (int j = 0; j < node.pointer.length; j++)
                if (node.pointer[j] != null)
                    if (j == 26)
                        suggest(item, node.pointer[j], '\'', str+ch, currentR);
                    else
                        suggest(item, node.pointer[j], (char)(j+'a'), str+ch, currentR);
    }

    private void addItem(int element, TrieNode node, String str, char ch)
    {
        if (element == mLD && node.isWord)
            if (count < 3)
                arrSuggest[count++] = str + ch;
        if (element < mLD && node.isWord)
        {
            mLD = element;
            count = 0;
            arrSuggest = new String[3];
            arrSuggest[count++] = str + ch;
        }
    }

    private int countRow(char ch, String item, int i, int[] currentR, int[] privR, int r, int a, int d)
    {
        int iCost, rCost, dCost;
        iCost = currentR[i-1]+1;
        dCost = privR[i]+1;
        if (item.charAt(i-1) == ch)
        {
            rCost = privR[i-1];
        }
        else
        {
            rCost = privR[i-1] + 1;
        }
        currentR[i] = Math.min(iCost, Math.min(rCost, dCost));
        int result = currentR[i];
        return result;
    }
}