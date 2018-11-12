import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception

public class WordSearch{
    private char[][]data;
    //the random seed used to produce this WordSearch
    private int seed;

    //a random Object to unify your random calls
    private Random randgen;

    //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String>wordsToAdd;
    
    //all words that were successfully added get moved into wordsAdded.
    private ArrayList<String>wordsAdded;


    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
        data = new char[rows][cols];
        clear();
    }

    //Choose a randSeed using the clock random
    public WordSearch( int rows, int cols, String fileName) {
	ArrayList<String>wordsToAdd = new ArrayList<String>();
	ArrayList<String>wordsAdded = new ArrayList<String>();
        data = new char[rows][cols];
        clear();
	randgen = new Random();
	seed = randgen;
	try{
            File f = new File(fileName);//can combine
            Scanner in = new Scanner(f);//into one line
      
                //NOW read the file here...
      
    	}catch(FileNotFoundException e){
      	    System.out.println("File not found: " + fileName);
            //e.printStackTrace();
            System.exit(1);
    	}
	addAllWords();
    }
    
    //Use the random seed specified.
    public WordSearch( int rows, int cols, String fileName, int randSeed) {
	ArrayList<String>wordsToAdd = new ArrayList<String>();
	ArrayList<String>wordsAdded = new ArrayList<String>();
	data = new char[rows][cols];
        clear();
	randgen = new Random();
	seed = randgen;
	try{
            File f = new File(fileName);//can combine
            Scanner in = new Scanner(f);//into one line
      
                //NOW read the file here...
      
    	}catch(FileNotFoundException e){
      	    System.out.println("File not found: " + fileName);
            //e.printStackTrace();
            System.exit(1);
    	}
	addAllWords();
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
	for (int i = 0; i < data.length; i++) {
	    for (int a = 0; a < data[i].length; a++) {
	        data[i][a] = '_';
	    }
	}
    }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
	String grid = "";
	for (int i = 0; i < data.length; i++) {
	    for (int a = 0; a < data[i].length; a++) {
	        grid += data[i][a] + " ";
	    }
	    grid += "\n";
	}
	return grid;
    }

    public char[][] before(char[][] a){
	char[][] before = new char[a.length][a[0].length];
	for (int i = 0; i < a.length; i++) {
	    for (int b = 0; b < a[i].length; b++) {
	    	before[i][b] = a[i][b];
	    }
	}
	return before;
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
    public boolean addWordHorizontal(String word,int row, int col){
	char[][] previous = before(data);
	if ((col + word.length()) > data[row].length) {
	    return false;
	}
	for (int a = 0; a < word.length(); a++) {
	    if ((data[row][col + a] == '_') || (data[row][col + a] == word.charAt(a))) {
	        data[row][col + a] = word.charAt(a);
	    }
	    else {
	        data = previous;
	        return false;
	    }
	}
	return true;
    }

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
    public boolean addWordVertical(String word,int row, int col){
	char[][] previous = before(data);
	if ((row + word.length()) > data.length) {
	    return false;
	}
        for (int a = 0; a < word.length(); a++) {
	    if ((data[row + a][col] == '_') || (data[row + a][col] == word.charAt(a))) {
	        data[row + a][col] = word.charAt(a);
	    }
	    else {
		data = previous;
		return false;
	    }
	}
	return true;
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top left to bottom right, must fit on the WordGrid,
     *and must have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     */
    public boolean addWordDiagonal(String word,int row, int col){
        char[][] previous = before(data);
	if ((row + word.length()) > data.length) {
	    return false;
	}
        for (int a = 0; a < word.length(); a++) {
	    if ((data[row + a][col + a] == '_') || (data[row + a][col + a] == word.charAt(a))) {
	        data[row + a][col + a] = word.charAt(a);
	    }
	    else {
		data = previous;
		return false;
	    }
	}
	return true;
    }
}
