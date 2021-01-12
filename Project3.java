import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.*;
import java.util.ArrayList.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Class that reads an input file and produces an output file containing information on the words in the input file, the number 
 *  of times each word occurs, and the average length of the collision lists
 * @author Esther Shin 
 */
public class Project3{
  
  /**
   * Method that reads a file (document) and prints out (into another file) all the words encountered in the document along 
   *  with their number of occurrences in the document
   * @param input_file  the name of the file that you want to input 
   * @param output_file  the name of the output file that contains all of the words encountered in the document along with their number of occurrences in the document
   */
  public static void wordCount(String input_file, String output_file) throws IOException{
    String book = Files.lines(Paths.get(input_file), StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator()));
    HashTable h1 = new HashTable();
    h1.readThrough(book);
    h1.scan(output_file);
  }
  
  /**
   * main method: starts the program by creating 2 new Project3's - one inputs the toy file name and outputs the toy file's result; 
   *  the other inputs the official input text (from Guttenberg) and outputs that input file's results
   */
  public static void main(String[] args) throws IOException{
     Project3 p3 = new Project3();
     Project3 p3b = new Project3();
     p3.wordCount("toyFile.txt", "toyOutputFile.txt");
     p3b.wordCount("p3InputText.txt", "p3OutputFile.txt");
   } 
  
}