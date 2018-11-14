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
	wordsToAdd = new ArrayList<String>();
	wordsAdded = new ArrayList<String>();
        data = new char[rows][cols];
        clear();
	seed = (int)(Math.random()*100000);
	randgen = new Random(seed);
	try{
            readFile(fileName);
      
    	}catch(FileNotFoundException e){
      	    System.out.println("File not found: " + fileName);
            //e.printStackTrace();
            System.exit(1);
    	}
	addAllWords();
    }
    
    //Use the random seed specified.
    public WordSearch( int rows, int cols, String fileName, int randSeed) {
	wordsToAdd = new ArrayList<String>();
	wordsAdded = new ArrayList<String>();
	data = new char[rows][cols];
        clear();
	randgen = new Random(randSeed);
	seed = randSeed;
	try{
            readFile(fileName);
      
    	}catch(FileNotFoundException e){
      	    System.out.println("File not found: " + fileName);
            //e.printStackTrace();
            System.exit(1);
    	}
	addAllWords();
    }
    public void readFile(String file) throws FileNotFoundException {
	File f = new File(file);//can combine
        Scanner in = new Scanner(f);//into one line
	while(in.hasNext()){
            wordsToAdd.add(in.nextLine().toUpperCase());
	}
	in.close();
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
	String mod = "";
	String mod2 = "";
	for (int i = 0; i < data.length; i++) {
	    grid += "|";
	    for (int a = 0; a < data[i].length; a++) {
	        grid += data[i][a] + " ";
	    }
	    grid += "|" + "\n";
	}
	mod = wordsAdded.toString();
	mod2 = mod.substring(1, (mod.length() - 1));
	grid += "Words: " + mod2 + "(seed: " + seed + ")";
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


    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added in the direction rowIncrement,colIncrement 
     *Words must have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
     *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
     *@return true when: the word is added successfully. 
     *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
     *        OR there are overlapping letters that do not match
     */
    private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
	char[][] previous = before(data);
	if ((rowIncrement == 0) && (colIncrement == 0)) {
		return false;
	}
        for (int a = 0; a < word.length(); a++) {
	    int rows = 0;
	    int cols = 0;
	    if (rowIncrement < 0) {
		rows = (a * -1);
	    }
	    else if ((rowIncrement > 0)) {
		rows = a;
	    }
	    if (colIncrement < 0) {
		cols = (a * -1);
	    }
	    else if ((colIncrement > 0)) {
		cols = a;
	    }
	    if (((row + rows) == data.length) || ((row + rows) < 0) || ((col + cols) == data[0].length) || ((col + cols) < 0)) {
	        data = previous;
		return false;
	    }
	    if ((data[row + rows][col + cols] == '_') || (data[row + rows][col + cols] == word.charAt(a))) {
	        data[row + rows][col + cols] = word.charAt(a);
	    }
	    else {
		data = previous;
		return false;
	    }
	}
	return true;
    }
    /*[rowIncrement,colIncrement] examples:
     *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
     *[ 1,0] would add downwards because (row+1), with no col change
     *[ 0,-1] would add towards the left because (col - 1), with no row change
     */

    private void addAllWords() { 
	int rowinc = 0;
	int colinc = 0;
	String wordChosen = "";
	boolean yes = false;
	int fails = 0;
	while ((wordsToAdd.size() > 0) && (fails < 100)) {
	    wordChosen = wordsToAdd.get(Math.abs(randgen.nextInt() % wordsToAdd.size()));
	    yes = false;
	    rowinc = randgen.nextInt() % 2;
	    colinc = randgen.nextInt() % 2;
	    for (int c = 0; c < 200; c++) {
		if ((yes == false) && (addWord(wordChosen, (randgen.nextInt() % data.length), (randgen.nextInt() % data[0].length), rowinc, colinc) == true)) {
            	    yes = true;
		}
		if (yes == false) {
		    addWord(wordChosen, (randgen.nextInt() % data.length), (randgen.nextInt() % data[0].length), rowinc, colinc);
		}
	    }
	    if (yes == true) {
		wordsToAdd.remove(wordChosen);
		wordsAdded.add(wordChosen);
		fails = 0;
	    }
	    else {
		wordsToAdd.remove(wordChosen);
		fails += 1;
	    }
	}
    }

    private fillInRandomLetters() {
	for (int a = 0; a < data.length(); a++) {
	    for (int c = 0; c < data[0].length(); c++) {
		if (data[a][c] == '_') {
		    data[a][c] = ((char)("A" + Math.abs(randgen.nextInt() % 27));
		}
	    }
	}

    }

    public static void main(String[]args) {
	if(args.length > 0){
            seed = Integer.parseInt(args[3]);
	    row = Integer.parseInt(args[0]);
	    col = Integer.parseInt(args[1]);
	    fname = args[3];
	    WordSearch(row, col, fname, seed);
        }
    }

}
