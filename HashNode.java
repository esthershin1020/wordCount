/**
 * This class is a HashNode that each linked list in the array list is made up of
 * @author Esther Shin 
 */
public class HashNode{
  
  /**
   * stores the number of times a word occurs in the input file
   */
  private Integer frequency;
  
  /**
   * stores a word that the input file contains
   */
  private String word;
  
  /**
   * a reference to the next HashNode of the list
   */
  private HashNode next;
  
  /**
   * the constructor
   * @param frequency  the frequency (number of times) the word stored in the node occurs in the input file
   * @param word  a word that occurs in the input file
   */
  public HashNode(Integer frequency, String word){
    this.frequency = frequency;
    this.word = word;
  }
  
  /**
   * Sets the word to be denoted by a node 
   * @param word  the word that is to be denoted by the node
   */
  public void setWord(String word){
    this.word = word;
  }
  
  /**
   * Method that returns the word denoted by a node
   * @return the word denoted by the node 
   */
  public String getWord(){
    return word;
  }

  /**
   * Sets the frequency of occurrences of a word stored in the node
   * @param frequency  the frequency (number of times) of occurrences of the word stored in the node
   */
  public void setFrequency(Integer frequency){
    this.frequency = frequency;
  }

  /**
   * Method that returns the frequency of occurrences of a word stored in a node
   * @return the frequency of occurrences of a word stored in a node
   */
  public int getFrequency(){
    return frequency;
  }
  
  /**
   * Return the next HashNode of the linked list
   * @return the next HashNode of the linked list
   */
  public HashNode getNext(){
    return next;
  }
  
  /**
   * Sets the next HashNode to come after this HashNode
   * @param next  the HashNode that should come after this HashNode in the linked list
   */
  public void setNext(HashNode next){
    this.next = next;
  }
}