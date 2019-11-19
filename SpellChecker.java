import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Properties;

public class SpellChecker
{
    static BST cyTree;//tree store words in tree data structure if called
    static Trie cyTrie;//trie store words in trie data structure if called
    // static Structure dictionary;
    static ArrayList<String> input = new ArrayList<>();//input store words from input file in this array
    static String[] suggestArr;// suggestion array
    //this method helps to read english0 file and stores it in tree
    public static void readFileinTree() throws MalformedURLException
    {
        URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0");
        try
        {
            BufferedReader read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;
            while ((line = read.readLine()) != null)
                if (!line.isEmpty())
                 cyTree.insertNode(line.toLowerCase());
//                System.out.println(line.toLowerCase());
            read.close();
//            dictionary.print();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //this method helps to read english0 file and stores it in trie
    public static void readFileinTrie() throws MalformedURLException
    {
        URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0");
        try
        {
            BufferedReader read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;
            while ((line = read.readLine()) != null)
                if (!line.isEmpty())
                    cyTrie.insertNode(line.toLowerCase());
//                System.out.println(line.toLowerCase());
            read.close();
//            cyTree.print();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
//this method read the a1properties file and call tree it the storage = tree call tire otherwise
//it defalut trie if the file doesn't exist
    public static String readPropertyFile() throws Exception
    {
        cyTree = new BST();
        cyTrie = new Trie();
        String type;
        try {
            File file = new File("a1properties.txt");
            Scanner scan = new Scanner(file);
            String line;
            type = scan.nextLine();
            if (type.contains("trie"))
            {
                return "trie";
            }
            else
            {
                return "tree";
            }

        }catch (FileNotFoundException e) {
            System.out.println("File Doesn't Exist");
        } 
        return "trie";
    }
//open input file
    public static void openInput(String fileName)
    {
        try {
            Scanner scan = new Scanner(new File(fileName));
            // String line = scan.nextLine();
            // System.out.println(line);
            while (scan.hasNext())
            {
                String line = scan.nextLine();
                if (!line.isEmpty())
                    input.add(line);
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            System.out.println("file doesn't exist!");
        }
    }

    public static void main(String[] args) throws Exception {
        String inputCheck;
        String structureType = readPropertyFile();
        System.out.println(structureType);
        if (structureType.equals("tree")) {
           try {
               readFileinTree();
           } catch (MalformedURLException e) {
               e.printStackTrace();
           }
           openInput(args[0]);
           BufferedWriter output;
           FileWriter outputFile = new FileWriter(args[1]);
           output = new BufferedWriter(outputFile);
           for (int i = 0; i < input.size(); i++) {
               inputCheck = input.get(i).toLowerCase();
               if (cyTree .find(inputCheck)) {
                   output.write(inputCheck+"\n");
               }
               else
               {
                suggestArr = cyTree.suggest(inputCheck);
                for (int j = 0; j < suggestArr.length; j++){
                        if (suggestArr[j]!=null) {
                            output.write(suggestArr[j]+' ');
                        }
                    }
                    output.write('\n');
               }
           }
           output.close();
        }
        if (structureType.equals("trie"))
        {
            try {
               readFileinTrie();
            } catch (MalformedURLException e) {
               e.printStackTrace();
            }
            openInput(args[0]);
            BufferedWriter output;
            FileWriter outputFile = new FileWriter(args[1]);
            output = new BufferedWriter(outputFile);
            // output.write("Caroline\n");
            // for (int i = 0; i < 10; i++) {
            //     output.write("a\n");
            // }
            // output.write("cy\n");
            // output.close();
            for (int i = 0; i < input.size(); i++)
            {
                // FileWriter outputFile = new FileWriter(args[1]);
                // output = new BufferedWriter(outputFile);
                inputCheck = input.get(i).toLowerCase();
                // System.out.println("inputCheck: "+inputCheck);
                if (cyTrie.find(inputCheck))
                {
                    // output = new BufferedWriter(outputFile);
                    // System.out.println(inputCheck);
                    output.write(inputCheck+'\n');
                    // output.close(); 
                }
                else
                {
                    // output = new BufferedWriter(outputFile);
                    suggestArr = cyTrie.suggest(inputCheck);
                    for (int j = 0; j < suggestArr.length; j++){
                        // System.out.print(suggestArr[j]+' ');
                        if (suggestArr[j]!=null) {
//                            System.out.println("to be added: "+suggestArr[j]);
                            output.write(suggestArr[j]+' ');
                        }
                    }
                    output.write('\n');
                    
                }
                
            }
            output.close();
        }
    }
}