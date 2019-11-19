//the interface for both tree and trie 
//In tree and trie I have these three method
public interface Structure
{
    public boolean find(String item);
    public void insertNode(String item);
    public String[] suggest(String item);
}
