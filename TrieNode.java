//trie Node which include a pointer array 
//each elements of the array points to different alphabet 
//isWord store true and false to check if the word is in the English.0 file
public class TrieNode
{
     TrieNode[] pointer;
     boolean isWord;
     public TrieNode()
     {
         pointer = new TrieNode[27];
         isWord = false;
     }
}