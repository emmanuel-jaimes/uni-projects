import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles everything with word generation for the Wordle.
 */
public class Words {
    /**ArrayList containing the words from the file (gets edited upon pulling words)*/
    private final ArrayList<String> word = new ArrayList<>();
    /**ArrayList containing all the words from the file (does not get edited)*/
    private final ArrayList<String> fullWordList = new ArrayList<>();
    /**Number of words in the list (subtracted when a word is pulled from the list)*/
    private int count = 11435;

    /**
     * Words class constructor. Reads in the file with all the potential words of the day.
     */
    public Words() {
        readFile();
    }

    /**
     * Reads the input file using method "readIOFileToList".
     */
    public void readFile()
    {
        try {
            readIOFileToList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Take the input filename and read all the words store them
     * into arraylist word.
     * @throws IOException cannot read file
     */
    public void readIOFileToList() throws IOException {
        try {
            Scanner scan = new Scanner(Paths.get("/iahome/e/ej/ejaimes/IdeaProjects/team01_swd/src/words5let.csv"));
            while (scan.hasNext()) {
                String temp = scan.nextLine();
                //System.out.println(temp);
                word.add(temp);
                fullWordList.add(temp);
            }
        }
        catch (IOException e){
                e.printStackTrace();
            }
    }

    /**
     * Get a random word from list, and remove it from the list
     * @return a word
     */
    public String getRandomWord()
    {
        SecureRandom secureRandom = new SecureRandom();
        int randomNum = secureRandom.nextInt(count);
        String wordPerDay = word.get(randomNum);    // get the random word
        word.remove(randomNum);                     // remove that word from the list
        count--;                                    // decrease the size

        return wordPerDay;
    }

    /**
     * Check if the user enters a valid word that is in the word list
     * @param wordToCheck the word being checked
     * @return whether the word is in the word list or not
     */
    public boolean checkValidWord(String wordToCheck) {
        return fullWordList.contains(wordToCheck);
    }
}
