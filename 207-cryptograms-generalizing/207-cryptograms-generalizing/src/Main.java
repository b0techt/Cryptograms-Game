import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static Scanner userInput = new Scanner(System.in); // takes user input
	public static Players players = new Players();
	public static String test;
	static Game playCryptograms;
	public static Player p;
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String args[]) throws IOException {

		boolean gameInput = false;
		boolean done = false;
		boolean load = false;
		boolean playerExists = false;
		String u = null;
		
		
		String message = "Input 'letter' to play a letter cryptogram, input 'number' to play a number cryptogram: ";
		
		
		
		System.out.println("Starting Cryptograms...");
		System.out.print("Enter your username to continue: ");
		
		p = new Player(userInput.nextLine());
		players.savePlayers();
		// Move this 
		/*
		for (Player x : players.getPlayers()) {
			System.out.println(x.getNumCryptogramsCompleted());
			System.out.println(players.allPlayers.size());
		}
		
		*/
		if (players.playerExist(p) == true) {
			p = players.findPlayer(p);
			System.out.println("Player found! Loading details...");
		}
		else {
			System.out.println("New player! Adding...");
			players.addPlayer(p);
		}
				
		// After getting username, check if a saved game exists and whether or not the player wants to load it
		System.out.print("\nIf you'd like to load a game you have saved " + p.getUsername() + ", enter 'load' now, else enter 'play': ");
		boolean gameReady = false;
		String playLoad = "";
		while (!gameReady) {
			playLoad = userInput.next();
			userInput.nextLine();
			if (playLoad.equals("load")) {
				load = true;
				gameReady = true;
			}
			else if (playLoad.equals("play")) {
				load = false;
				gameReady = true;
			}
			else {
				System.out.print('\n');
				System.out.print("Invalid input, please enter 'load' to load a saved game or 'play' to play a new game: ");
				gameReady = false;
			}
		}
		
		// Game running
		while (!done) {
			if (load == true) {
				Scanner s = new Scanner(new File("cryptogram_state.txt"));
				boolean found = false;
				
				while (s.hasNext()) {
					String[] current = s.next().split(",");
					if (current[0].equals(p.getUsername())) {
						found = true;
					}
				}
				if (found == false) {
					System.out.println("Error. No saved progress exists for this user, start the app again and request a new game. Exiting...");
					test = "there was no file";
					done = true;
					//Break only when testing
					s.close();
					return;
					//System.exit(1);
				}
			
				System.out.println("Loaded game...\n");
				playCryptograms = new Game(p);
				playCryptograms.playGame();
				
				p.updateAccuracy();
				
				System.out.println("\nThe current player is: " + playCryptograms.currentPlayer.getUsername());	// ensures the player loaded correctly/wipe out play error
			}
			else {
				System.out.print("\n" + message);
				String gameType = null;

				while (!gameInput) { // Set type of cryptogram to play based on user input
					gameType = userInput.next();
					if (gameType.equals("letter") || gameType.equals("number")) {
						gameInput = true;
					}
					else {
						System.out.println("Invalid input, please try again.");
						System.out.print('\n');
						System.out.print(message);
					}
				}

				playCryptograms = new Game(p, gameType);
				playCryptograms.playGame();
				System.out.print('\n');
				p.updateAccuracy();
				
				System.out.println("The current player is: " + playCryptograms.currentPlayer.getUsername());	// ensures the player loaded correctly/wipe out play error
			}

			System.out.println("Total Guesses: " + p.getTotalGuesses() 
					+ " Total number of correct guesses: " + p.getNumCorrectGuesses() 
					+ " Total completed crytograms: " + p.getNumCryptogramsCompleted()
					+ " Total number of cryptograms played: " + p.getNumCryptogramsPlayed() 
					+ " Accuracy: " + p.getAccuracy() + "%");
			
			System.out.print("Would you like to continue playing? (y/n): ");
			u = userInput.next();

			boolean check = false;
			while (!check) {
				if (u.equals("y")) {
					gameInput = false;
					load = false;
					check = true;
				} 
				else if (u.equals("n")) {
					System.out.println("Exiting...");
					done = true;
					check = true;
				} 
				else {
					System.out.print("This input is invalid. Please enter 'y' to continue, or 'n' to exit: ");
					u = userInput.next();
				}
			}
		}	
		//players.addPlayer(p);
		//System.out.println(p.getNumCorrectGuesses());
		players.updatePlayerArray(p);
		players.savePlayersToFile();

		userInput.close();
	}
}
	