import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/*
* Game class
* Generates and manipulates cryptograms
*/

public class Game<E> {

	public int showSolution = 0;
	public int displayFreq = 0;
	public int displayStats = 0;
	public int hinted = 0;
	//^^used for testing
	public HashMap<Object, Character> playerGameMapping;
	public Player currentPlayer;
	public Cryptogram<E> crypt;
	private static File savedCrypts = new File("cryptogram_state.txt");
	private int statement;
	public boolean finished = false;
	public ArrayList<String[]> saved = new ArrayList<String[]>();
	public ArrayList<Integer> listScore = new ArrayList<>();
	public Iterator<Integer> topScores;
	private String cryptType;
	public String filename = "cryptograms.txt";
	private boolean loaded = false;
	

	/*
	 * Constructor, for when a player is given to load details
	 */
	public Game(Player p) throws IOException {
		getSavedCrypts();
		currentPlayer = p;
		playerGameMapping = new HashMap<Object, Character>();
		loaded = true;
		loadGame();
		crypt.fill();
		crypt.getFrequencies();

	}

	/*
	 * Constructor, generates a game for the player based on the inputted cryptogram
	 * type
	 */
	public Game(Player p, String cryptType) throws IOException {
		currentPlayer = p;
		
		boolean exit = false;
		playerGameMapping = new HashMap<Object, Character>();
		this.cryptType = cryptType;
	
		while (!exit) {
			if (cryptType.contains("letter")) {
				generateLetterCryptogram();
				statement = 1;
				exit = true;
			} 
			else if (cryptType.contains("number")) {
				generateNumberCryptogram();
				statement = 2;
				exit = true;
			} 
			else {
				System.out.println("Invalid cryptogram type, please re-input your choice: ");
			}
		}

	}

	/*
	 * Generates a cryptogram puzzle mapped with letters
	 */
	@SuppressWarnings("unchecked")
	public void generateLetterCryptogram() throws IOException {
		System.out.println("Letter Cryptogram & Mapping: ");
		crypt = (Cryptogram<E>) new LetterCryptogram(filename);
		getSavedCrypts();
		crypt.getFrequencies();
		crypt.fill();
	}

	/*
	 * Generates a cryptogram puzzle mapped with numbers
	 */
	@SuppressWarnings("unchecked")
	public void generateNumberCryptogram() throws IOException {
		System.out.println("Number Cryptogram & Mapping: ");
		crypt = (Cryptogram<E>) new NumberCryptogram(filename);
		getSavedCrypts();	
		crypt.getFrequencies();
		crypt.fill();
	}

	/*
	 * Loads player details
	 */
	public void loadPlayer() {
		System.out.println("Displaying "+currentPlayer.getUsername()+"'s details: \n");
		System.out.println("Username: " + currentPlayer.getUsername()
			+ "\nAccuracy: " + currentPlayer.getAccuracy()
			+ "\nTotal Guesses: " + currentPlayer.getTotalGuesses()
			+ "\nCorrect Guesses: " + currentPlayer.getNumCorrectGuesses()
			+ "\nCryptograms Played: " + currentPlayer.getNumCryptogramsPlayed()
		    + "\nCryptograms Completed: " + currentPlayer.getNumCryptogramsCompleted());	
		
		
	}

	/*
	 * Once a player is loaded, this initiates the generated game
	 */
	public void playGame() throws IOException {
		if (!loaded) {
			currentPlayer.incrementCryptogramsPlayed();
		}
		while (!finished) {
			// Prints for purpose of marking
			System.out.print("\n"); 
			System.out.println("The phrase being used to generate the cryptogram is: " + crypt.phrase);
			System.out.println("The cryptogram mapping is: " + crypt.cryptogramAlphabet);
			System.out.print("\n"); 
			
			// Prints menu
			System.out.println("Menu options: ");
			System.out.print("1 - Enter Letter or Number \n"
					+ "2 - Undo Letter or Number\n"
					+ "3 - View Frequencies in phrase\n"
					+ "4 - Show Solution\n"
					+ "5 - Save current cryptogram \n"
					+ "6 - Display Details \n"
					+ "7 - Get hint \n"
					+ "8 - Show top 10 scores \n"
					+ "9 - Exit \n"
					+ "\n"
					+ "Input the number corresponding to your chosen action: ");
			
			String answer = Main.userInput.next();

			if (answer.equals("1")) {
				enterLetters();
				checkCompletion();
			} 
			else if (answer.equals("2")) {
				System.out.print('\n');
				undo();
			} 
			else if (answer.equals("3")) {
				System.out.print('\n');
				viewFrequencies();
			} 
			else if (answer.equals("4")) {
				System.out.print('\n');
				showSolution();
			} 
			else if (answer.equals("5")) {
				try {
					saveGame();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (answer.equals("6")) {
				try {
					System.out.print('\n');
					loadPlayer();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if(answer.equals("7")) {
				System.out.print('\n');
				getHint();
			}
			else if(answer.equals("8")) {
				System.out.print('\n');
				seeTop10Scores();
			}
			else if(answer.equals("9")) {
				System.out.println("Exitting...");
				Main.players.updatePlayerArray(currentPlayer);
				Main.players.savePlayersToFile();
				//comment break for running, comment exit for testing
				//System.exit(1);
				break;
			}

			System.out.print('\n');
			update();

		}
	}

	/*
	 * Allows the user to input a single letter into the cryptogram puzzle
	 */
	public void enterLetters() {
		System.out.print("Enter which mapping you'd like to change: ");
		String key = Main.userInput.next();

		System.out.print("Enter a value:");
		char value = Main.userInput.next().charAt(0);

		crypt.enterLetter(key, value, playerGameMapping);

		if (crypt.checkGuess(key, value)) {
			currentPlayer.incrementNumCorrectGuesses();
		}

		currentPlayer.incrementNumGuesses();
		System.out.println(playerGameMapping);
	}

	public void undo() {
		System.out.print("Which key would you like to undo?: ");
		String key = Main.userInput.next();

		if (crypt.keyCheck(key, playerGameMapping, statement)) {
			crypt.remove(key, playerGameMapping);
		} 
		else {
			System.out.println("The value is not a key or empty!");
		}

	}

	public void update() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println("Current mapping: \n");
		
		for (char c : crypt.phrase.toLowerCase().toCharArray()) {
			if (crypt.cryptogramAlphabet.get(c) == null) {
				System.out.print(' ');
			} 
			else {
				Object x = crypt.cryptogramAlphabet.get(c);
				
				if (playerGameMapping.containsKey(x)) {
					System.out.print("|" + playerGameMapping.get(x) + " |");
				} 
				else {
					System.out.print("|__|");
				}
			}
		}
		System.out.print("\n");
		crypt.printMap();
		System.out.print("\n");
	}

	public void checkCompletion() {
		if (playerGameMapping.keySet().size() == crypt.cryptogramAlphabet.values().size()) {
			@SuppressWarnings("unused")
			boolean allc = true;
			
			for (char c : crypt.cryptogramAlphabet.keySet()) {
				if (playerGameMapping.get(crypt.cryptogramAlphabet.get(c)) == c) {
					allc = true;
				} 
				else {
					allc = false;
					System.out.print("\n");
					System.out.println("Your solution is incorrect. Please continue: ");
					return;
				}
			}
			
			System.out.print("\n");
			System.out.println("Your solution is correct!");
			finished = true;
			currentPlayer.incrementCryptogramsCompleted();
		}
	}

	/*
	 * Displays the frequencies of each letter in the currently loaded puzzle
	 */
	public void viewFrequencies() {
		HashMap<Character, Double> mid = new HashMap<>();
		System.out.print("Frequencies of each character in the phrase: ");
		System.out.println(crypt.frequencies);
		System.out.println("");
		for(char c : crypt.cryptogramAlphabet.keySet()) {
			mid.put(c, crypt.english.get(crypt.cryptogramAlphabet.get(c)));
		}
		System.out.print("Proportion of the letter in the English language: ");
		System.out.println(mid);
		displayFreq = 1;
	}

	public void getSavedCrypts() throws IOException {
		Scanner filescan = new Scanner(savedCrypts);
		
		while (filescan.hasNext()) {
			String[] line = filescan.next().split(",");
			saved.add(line);
		}
		
		filescan.close();

	}
	
	/*
	 * Saves the current state of the game
	 *
	 */
	public void saveGame() throws IOException {
		boolean saveExists = false;
		Scanner check = new Scanner(new File("cryptogram_state.txt"));

		while (check.hasNext()) {
			String[] current = check.next().split(",");
			if (current[0].equals(currentPlayer.getUsername())) {
				saveExists = true;
			}
		}
		
		if (saveExists == true) {
			boolean decision = false;
			while (!decision) {
				System.out.println("Are you sure you'd like to overwrite your previously saved game? (y/n): ");
				
				String yn = Main.userInput.next();
				if (yn.equals("y")) {
					System.out.println("Saving...");
					
					decision = true;
				}
				else if (yn.equals("n")) {
					System.out.println("Cancelling save procedure...");
					
					decision = true;
					return;
				}
				else {
					System.out.println("Invalid input, try again: ");
					
					decision = false;
				}
			}
		}
		
		PrintWriter pw = new PrintWriter("cryptogram_state.txt");
		FileWriter fw = new FileWriter("cryptogram_state.txt", true);
		
		pw.write("");
		saved.removeIf(x -> x[0].equals(currentPlayer.getUsername()));
		
		for (String[] s : saved) {
			fw.write(s[0] + "," + s[1] + "," + s[2] + "," + s[3] + "," + s[4] + "\n");
		}
		
		fw.write(currentPlayer.getUsername() + "," + crypt.phrase + "," );		
    	for (Object key : playerGameMapping.keySet()) {
    		fw.write(key + "=" + playerGameMapping.get(key) + "|");
    	}	
    	fw.write(",");	
    	for (char key : crypt.cryptogramAlphabet.keySet()) {
    		fw.write(key + "=" + crypt.cryptogramAlphabet.get(key) + "|");
    	}
    	
    	fw.write( "," + cryptType+"\n");
    	pw.close();
    	fw.close();
    	
    	System.out.println("Successfully saved.");
    }

	 /*
	    * Loads a previous state of the game
	    */
	    @SuppressWarnings("unchecked")
		public void loadGame() throws FileNotFoundException {
	    	for (String[] s : saved) {
	    		if (s[0].equals(currentPlayer.getUsername())) {
	    			if (s[4].contains("letter")) {
	    				cryptType = "letter";
	    				crypt = (Cryptogram<E>) new LetterCryptogram();
	    				crypt.phrase = s[1];
	    				crypt.cryptogramAlphabet = new HashMap<>();
	    				
	    				for(String sub : s[2].split("\\|")) {
	    					String[] mid = sub.split("=");
	    					
	    					if(mid.length > 1) {
	    						playerGameMapping.put(mid[0].charAt(0), mid[1].charAt(0));
	    					}
	    				}
	    				crypt.fillAlphabet(s[3]);
	    				
	    				statement = 1;
	    				update();	    				
	    			}
	    			else if(s[4].contains("number")) {
	    				cryptType = "number";
	    				crypt = (Cryptogram<E>) new NumberCryptogram();
	    				crypt.phrase = s[1];
	    				crypt.cryptogramAlphabet = new HashMap<>();
	    				int val = 0;

	    				for(String sub : s[2].split("\\|")) {
	    					String[] mid = sub.split("=");
	    					
	    					if(mid.length > 1) {
	    						val = Integer.parseInt(mid[0]);
	    						playerGameMapping.put(val, mid[1].charAt(0));
	    					}
	    				}
	    				crypt.fillAlphabet(s[3]);
	    				
	    				statement = 2;
	    				update();
	    			}
	    		}
	    	}
	    }
	    
	/*
	 * Shows the solution to the current cryptogram puzzle
	 */
	public void showSolution() {
		System.out.println("Solution with mapping: ");
		
		for (char c : crypt.phrase.toLowerCase().toCharArray()) {
			if (c == ' ' || c == '.') {
				System.out.print(' ');
			} 
			else {
				System.out.print("|" + c + " |");
			}
		}
		showSolution = 1;
		System.out.println();
		crypt.printMap();
		System.out.println("\nPhrase: " + crypt.phrase);
	}
	
	
	/*
	 * Displays top 10 scores based on cryptograms completed
	 */
	public void seeTop10Scores() throws IOException {
		System.out.println("Showing Top 10 scores");
		System.out.println();
	
		File scores = new File("players.txt");
		if (scores.length() == 0) {
			System.out.println("Error: No users stored, cannot display scores.");
			return;
		}
		
		Collections.sort(Main.players.getPlayers());
		int i = 1;
		displayStats = 1;
		for(Player x : Main.players.getPlayers()) {
			if(i != 11 ) {
				if(x.getNumCryptogramsCompleted() == 0) {
					break;
				}
				System.out.println(i+". Username: "+x.getUsername()+" and Score: " + x.getNumCryptogramsCompleted());
				i++;
			}
		
		}
		
	    
	}
	
	/*
	 * Displays a hint in the cryptogam
	 */
	public void getHint() {
		System.out.print("Getting a hint...");
		hinted = 0;
		if (playerGameMapping.keySet().size() == crypt.cryptogramAlphabet.keySet().size()) {

			for (char x : crypt.cryptogramAlphabet.keySet()) {
				if (x != playerGameMapping.get(crypt.cryptogramAlphabet.get(x))) {
					playerGameMapping.replace(crypt.cryptogramAlphabet.get(x), x);
					System.out.println("Added Hint");
					update();
					checkCompletion();
					hinted = 1;
					return;
					
				}
			}
		}
		else {
			for (char x : crypt.cryptogramAlphabet.keySet()) {
				if (!playerGameMapping.containsKey(crypt.cryptogramAlphabet.get(x))) {
					playerGameMapping.put(crypt.cryptogramAlphabet.get(x), x);
					System.out.println("Added Hint");
					update();
					checkCompletion();
					hinted = 1;
					return;
				}
			}
		}
	}
	
}
