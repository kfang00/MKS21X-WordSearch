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

    //Choose a randSeed using the clock random
    public WordSearch( int rows, int cols, String fileName) {
	helper(rows, cols, fileName);
	seed = (int)(Math.random() * 10000);
	randgen = new Random(seed);
	addAllWords();
	fillInRandomLetters();
    }
    
    //Use the random seed specified.
    public WordSearch( int rows, int cols, String fileName, int randSeed) {
	helper(rows, cols, fileName);
	randgen = new Random(randSeed);
	seed = randSeed;
	addAllWords();
	fillInRandomLetters();
    }

    public WordSearch( int rows, int cols, String fileName, int randSeed, boolean ans) {
	helper(rows, cols, fileName);
	randgen = new Random(randSeed);
	seed = randSeed;
	if (ans == false) {
	    addAllWords();
	    fillInRandomLetters();
	}
	else {
	    addAllWords();
	}
    }

    public void helper(int R, int C, String FN) {
	wordsToAdd = new ArrayList<String>();
	wordsAdded = new ArrayList<String>();
	data = new char[R][C];
        clear();
	try{
            readFile(FN);
      
    	}catch(FileNotFoundException e){
      	    System.out.println(" 1. Check if you have enough arguments to run the program. You must have three or more!! \n 2. Check if you have the right file name. \n 3. Check if your numerical arguments are properly formatted!! (must be int) \n 4. Check if your numerical arguments are out of range. Row/col must be > 0 and the seed must be from 0 to 10000 inclusive!");
            //e.printStackTrace();
            System.exit(1);
    	}
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
	        data[i][a] = ' ';
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
	    if ((data[row][col + a] == ' ') || (data[row][col + a] == word.charAt(a))) {
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
	    if ((data[row + a][col] == ' ') || (data[row + a][col] == word.charAt(a))) {
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
	    if ((data[row + a][col + a] == ' ') || (data[row + a][col + a] == word.charAt(a))) {
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
	    if ((data[row + rows][col + cols] == ' ') || (data[row + rows][col + cols] == word.charAt(a))) {
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
	    rowinc = randgen.nextInt() % 2;
	    colinc = randgen.nextInt() % 2;
	    for (int c = 0; c < 200 && !yes; c++) {
		if (addWord(wordChosen, (randgen.nextInt() % data.length), (randgen.nextInt() % data[0].length), rowinc, colinc)) {
		    yes = true;
		}
	    }
	    if (yes == true) {
		wordsToAdd.remove(wordChosen);
		wordsAdded.add(wordChosen);
		fails = 0;
		yes = false;
	    }
	    else {
		fails += 1;
	    }
	}
    }

    private void fillInRandomLetters() {
	for (int a = 0; a < data.length; a++) {
	    for (int c = 0; c < data[0].length; c++) {
		if (data[a][c] == ' ') {
		    data[a][c] = ((char)('A' + Math.abs(randgen.nextInt() % 26)));
		}
	    }
	}

    }

    public static void main(String[]args) {
	int seed = 0 ;
	boolean a = false;
	String s = " 1. Check if you have enough arguments to run the program. You must have three or more!! \n 2. Check if you have the right file name. \n 3. Check if your numerical arguments are properly formatted!! (must be int) \n 4. Check if your numerical arguments are out of range. Row/col must be > 0 and the seed must be from 0 to 10000 inclusive!";
	try{
	    if (args.length > 0) {
                Integer.parseInt(args[0]);
	    }
	    if (args.length > 1) {
	        Integer.parseInt(args[1]);
	    }
	    if (args.length > 3) {
		Integer.parseInt(args[3]);
	}
      
    	}catch(NumberFormatException e){
      	    System.out.println(s);
            System.exit(1);
    	}
	if(args.length == 0){
	    System.out.println(s);
	    System.exit(1);
	}
	if(args.length < 3){
	    System.out.println(s);
	    System.exit(1);
	}	
	else {
	    if ((Integer.parseInt(args[0]) < 0) || (Integer.parseInt(args[1]) < 0)) {
		System.out.println(s);
		System.exit(1);
	    }
	    
	    if (args.length > 3) {
		seed = Integer.parseInt(args[3]);
		if ((seed < 0) || (seed > 10000)) {
		    System.out.println(s);
		    System.exit(1);
		}
	    }
	    if (args.length > 4) {
		seed = Integer.parseInt(args[3]);
		if (args[4].equals("key")) {
		    a = true;
		}
		else {
		    a = false;
		}
	        WordSearch w = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2], seed, a);
	        System.out.println(w.toString());
	    }
	    else if (args.length > 3) {
	        seed = Integer.parseInt(args[3]);
	        WordSearch w = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2], seed);
	        System.out.println(w.toString());
	    }
	    else {
	        WordSearch w = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2]);
	        System.out.println(w.toString());
	    }
	}   
    }

}
