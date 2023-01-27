import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* Players class
*  Holds multiple player objects
*/

@SuppressWarnings("unused")
public class Players {

	public ArrayList<Player> allPlayers = new ArrayList<Player>();
	public ArrayList<String[]> savedPlayers = new ArrayList<String[]>();
	private static File playersFile = new File("players.txt");
	private static Player onePlayer;

	/*
	 * Adds a player
	 */
	public void addPlayer(Player p) {
		try {
			onePlayer = p;
			//FileWriter fw = new FileWriter(playersFile, true);
			
			if (playerExist(p) == true) {
				//fw.close();				
				return;
			}
			
			else {
				/*
				fw.write(onePlayer.getUsername() 
						+ " " + onePlayer.getAccuracy() 
						+ " " + onePlayer.getTotalGuesses() 
						+ " " + onePlayer.getNumCorrectGuesses() 
						+ " " + onePlayer.getNumCryptogramsPlayed() 
						+ " " + onePlayer.getNumCryptogramsCompleted() + "\n");
				*/
				//fw.close();
				
				allPlayers.add(p);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Saves the current list of players
	 */
	public void savePlayers() throws IOException {
    	String curent;
    	String[] array;
    	try {
    		Scanner fr = new Scanner(playersFile);
    		while(fr.hasNext()) {
    			curent = fr.nextLine();
    			array = curent.split("[ \\t\\n\\x0b\\r\\f]");
    			onePlayer = new Player(array[0]);
    			onePlayer.setAccuracy(Double.parseDouble(array[1]));
    			onePlayer.setTotalGuesses(Integer.parseInt(array[2]));
    			onePlayer.setCorrectGuesses(Integer.parseInt(array[3]));
    			onePlayer.setCryptogramsPlayed(Integer.parseInt(array[4]));
    			onePlayer.setCryptogramsCompleted(Integer.parseInt(array[5]));
    			allPlayers.add(onePlayer);
    		}
    		fr.close();
    	}
    	catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }

		
	public void savePlayersToFile() throws IOException {
		PrintWriter pw = new PrintWriter(playersFile);
		FileWriter fw = new FileWriter(playersFile, true);
		//int i = 0; i < savedPlayers.size(); i++
		pw.write("");
		for (Player p : allPlayers) {
			//String[] s = Arrays.toString(savedPlayers.get(i)).split(" ");
			
			//if (s[0].equals(p.getUsername())) {	
				//savedPlayers.remove(i);
			fw.write(p.getUsername() 
					+ " " + p.getAccuracy() 
					+ " " + p.getTotalGuesses() 
					+ " " + p.getNumCorrectGuesses() 
					+ " " + p.getNumCryptogramsPlayed() 
					+ " " + p.getNumCryptogramsCompleted() + "\n");
			}
		/*
			else {
				System.out.println(s[0]);
				fw.write(s[0] + " " + s[1] + " " + s[2] + " " + s[3] + " " + s[4] + " " + s[5] + "\n");
			}
		*/
		pw.close();
		fw.close();
	}
		
	
	
	public void getSavedPlayers() throws IOException {
		Scanner filescan = new Scanner(playersFile);
		
		while (filescan.hasNext()) {
			String[] line = filescan.next().split(" ");
			savedPlayers.add(line);
		}
		
		filescan.close();
	}

	/*
	 * Finds a player currently in the list
	 */
	public Player findPlayer(Player p) {
		Player foundP = new Player("Empty!");
		String[] playerDetails = null;
		String current = null;
		
		try {
			Scanner fr = new Scanner(playersFile);
			while (fr.hasNext()) {
				current = fr.nextLine();
				playerDetails = current.split(" ");
				
				current = playerDetails[0];
				if (p.getUsername().equals(current)) {
					foundP.setUsername(current);
					foundP.setAccuracy(Double.parseDouble(playerDetails[1]));
					foundP.setTotalGuesses(Integer.parseInt(playerDetails[2]));
					foundP.setCorrectGuesses(Integer.parseInt(playerDetails[3]));
					foundP.setCryptogramsPlayed(Integer.parseInt(playerDetails[4]));
					foundP.setCryptogramsCompleted(Integer.parseInt(playerDetails[5]));
				}
			}
			
			fr.close();

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return foundP;
	}
	
	public boolean playerExist(Player p) {
		boolean found = false;	
		String[] playerDetails = null;
		String current = null;
		
		try {
			Scanner fr = new Scanner(playersFile);
			while (fr.hasNext()) {
				current = fr.nextLine();
				playerDetails = current.split(" ");
				current = playerDetails[0];
								
				if (p.getUsername().equals(current)) {
					found = true;
				}
			}
			fr.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return found;
	}

	public void updatePlayerArray(Player player){
		for(Player p : allPlayers) {
			if(p.getUsername().equals(player.getUsername())) {
				allPlayers.remove(p);
				allPlayers.add(player);
				return;
			}
		}
	}
	
	/*
	 * Sum of all player accuracies
	 */
	public double getAllPlayersAccuracies() {
		// TODO change return type and add body
		return 0;
	}

	/*
	 * Gets all players cryptograms played Total count of cryptograms played
	 */
	public int getAllPlayersCryptogramsPlayed() {
		// TODO change return type and add body
		return 0;
	}

	/*
	 * Total count of completed cryptograms
	 */
	public int getAllPlayersCompletedCryptos() {
		// TODO change return type and add body
		return 0;
	}

	public ArrayList<Player> getPlayers() {
		return allPlayers;
	}

}
