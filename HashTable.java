import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.*;
import java.util.ArrayList.*;
import java.util.LinkedList.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Class that creates a hash table containing all of the words and their corresponding frequencies, and then outputs them into an output file 
 * @author Esther Shin 
 */
public class HashTable{
  
  /**
   * a hash table (in this case, an array list of linked lists of HashNodes) that keeps the current counts for words you have already 
   *  encountered while you are scanning the input file
   */
  private ArrayList<LinkedList<HashNode>> hashTable = new ArrayList<LinkedList<HashNode>>(500000);
  
  /**
   * stores the the current size of the array list (in other words, the number of indices in the array list that are full/not null)
   */
  private int currentSize = 0;

  /**
   * stores the loadFactor that will be used to decide whether or not to resize the hash table
   */
  private double loadFactor = 0;
  
  /**
   * stores the current size of the hash table, including the null/empty indices of the array list
   */
  private int hashTableSize = 500000;
  
  /**
   * stores the number of hashNodes that are created
   */
  private int numberOfNodes = 0;
  
  /**
   * the constructor 
   */
  public HashTable(){
    /**
     * goes through the entire hashTable and makes the indices null so that they exist and are initialized
     */
    for(int index = 0; index < hashTableSize; index = index + 1){
      hashTable.add(null);
    }
  }
  
  /**
   * Method that goes through the input file string, extract words from an input string, and then inserts the words into the hash table
   * @param book  the string of the input file that will be read in order to produce the output file 
   */
  public void readThrough(String book) throws IOException{
    book.split("\\W");
    /**
     * Goes through array of all of the words in the input String, and then inserts them into the hash table
     */
    for(int index = 0; index < book.split("\\W").length; index = index + 1){
      insert(book.toLowerCase().split("\\W")[index].toString(), 1);
    }
  }
  /**
   * Helper method for "readThrough" that inserts each word in the input file (in the form of an array at this point) into the hash table
   * @param word  the word (from the input file) being inserted into the hash table
   * @return the updated hash table after inserting the word specified by the parameters 
   */
  public ArrayList<LinkedList<HashNode>> insert(String word, Integer frequency){   
    loadFactor = currentSize/hashTableSize;
    if(loadFactor >= 0.5){
      resize();
    } 
    else{
      
      /**
       * hashFunction: stores the hash function, which signifies the index of the array at which the word will be inserted
       */
      int hashFunction = Math.abs(word.hashCode()) % hashTableSize;
      if(lookUp(word) == false){ //if the word does not exist in the hashTable
        
        /**
         * node: stores the new word being inserted into the hash table along with its frequency (which is 1 since it is new)
         */
        HashNode node = new HashNode(frequency, word);
        if(hashTable.get(hashFunction) == null){ 
          /**
           * l1: stores the linked list that will contain the new node that contains the new word to be inserted into the hash table
           */
          LinkedList<HashNode> l1 = new LinkedList<HashNode>();
          l1.add(node);
          hashTable.set(hashFunction, l1); 
          currentSize = currentSize + 1;
          numberOfNodes = numberOfNodes + 1;
        }
        else{ 
          hashTable.get(hashFunction).addLast(node);
          numberOfNodes = numberOfNodes + 1;
        } 
      }
      else{
        /**
         * Goes through the linked list contained at the hash function index of the word input parameter
         *  As you loop through the linked list, when the hashNode with the same word is found, its frequency is updated by 1
         */
        for(int index = 0; index < hashTable.get(hashFunction).size(); index = index + 1){
          if(hashTable.get(hashFunction).get(index).getWord().equals(word)){
            hashTable.get(hashFunction).get(index).setFrequency(hashTable.get(hashFunction).get(index).getFrequency() + 1);
          }
        }
      }
    }
    return hashTable;
  }
  
  /**
   * Helper method used in "insert" that looks up whether or not a word exists in the hash table already
   * @param word  the word that is being searched for in the hash table to see if it exists already or not 
   * @return true if the word already exists in the hash table, and false if it does not exist in the hash table
   */
  public boolean lookUp(String word){
    
    /**
     * hashFunction: stores the hash function, which signifies the index of the array at which the word would exist 
     */
    int hashFunction = Math.abs(word.hashCode()) % hashTableSize;
    if(hashTable.get(hashFunction) != null){
      /**
       * Goes through the linked list at the index (hash function) of the array list where the word would exist if it is in the hash table
       */
      for(int index = 0; index < hashTable.get(hashFunction).size(); index = index + 1){
        if(hashTable.get(hashFunction).get(index).getWord().equals(word)){
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * Helper method used in "insert" to resize the hash table (array list) if the load factor becomes greater than 0.5
   */
  public void resize(){
    
    /**
     * newHashTable: stores the new hash table that is of the updated (increased/resized) hash table size
     */
    ArrayList<LinkedList<HashNode>> newHashTable = new ArrayList<LinkedList<HashNode>>(hashTableSize*2);
    /**
     * temp: stores a duplicate of the hash table before it is resized 
     */
    ArrayList<LinkedList<HashNode>> temp = new ArrayList<LinkedList<HashNode>>(hashTableSize);
    /**
     * Deep copies the old hashtable to the temp
     */
    for(int index = 0; index < hashTableSize; index = index + 1){
      temp.add(hashTable.get(index));
    }
    hashTable = newHashTable;
    /**
     * oldSize: stores the size of the hashTable before it resizes
     */
    int oldSize = hashTableSize;
    hashTableSize = hashTableSize*2;
    currentSize = 0; //this is because you are starting from scratch
    
    /**
     * Populates the arraylist
     */
    for(int index = 0; index < hashTableSize; index = index + 1){
      hashTable.add(null);
    }
    /**
     * Re-inserts each node into the newly resized hash table
     */
    for(int index = 0; index < oldSize; index = index + 1){
      LinkedList<HashNode> nodeList = temp.get(index);
      if(nodeList != null){
        for (HashNode hashNode : nodeList) {
          insert(hashNode.getWord(), hashNode.getFrequency());
        }
      }    
    }
  }
  

  /**
   * Method that scans through the entire hash table and produces an output file containing information on the words in the hash table, the number 
   *  of times each word occurs, and the average length of the collision lists
   * @param outputFile  the name of the output file where the results will be printed to 
   */
  public void scan(String outputFile) throws IOException{ 
    /**
     * writer: writes the average length of the collision lists AND all of the words with their corresponding frequencies into an output file 
     */
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    /**
     * aveLengthColl: stores the average length of the collision lists
     */
    double aveLengthColl;
    aveLengthColl = ((double)numberOfNodes)/((double)hashTableSize);
    writer.write("Average Length of the Collision Lists: " + aveLengthColl + "\n");
    /**
     * Goes through each index of the hash table (array list)
     */
    for(int index = 0; index < hashTableSize || index < hashTable.size(); index = index + 1){
      /**
       * Goes through each linked list at the array list index and writes the word and corresponding frequency stored in the nodes of each linked list
       */
      for(int index2 = 0; hashTable.get(index) != null && index2 < hashTable.get(index).size(); index2 = index2 + 1){
        writer.write("(" + hashTable.get(index).get(index2).getWord() + " " + hashTable.get(index).get(index2).getFrequency() + ") ");
      }
    }
    writer.close();
  }
}