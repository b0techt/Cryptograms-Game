/*
*  Player class
*  Creates object type player
*/

public class Player implements Comparable<Player>{

	// Attributes
	private String username;
	private double accuracy;
	private int totalGuesses;
	private int numCorrectGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;

	public Player(String username) {
		this.username = username;
		accuracy = 0.0;
		totalGuesses = 0;
		numCorrectGuesses = 0;
		cryptogramsPlayed = 0;
		cryptogramsCompleted = 0;
	}

	/*
	 * Updates a player's accuracy, based on new guesses
	 */
	public double updateAccuracy() {
		accuracy = ((double) numCorrectGuesses / (double) totalGuesses) * 100;
		return accuracy;
	}

	/*
	 * Increments cryptograms completed When a player completes a cryptogram, this
	 * should be called
	 */
	public void incrementCryptogramsCompleted() {
		cryptogramsCompleted++;
	}

	/*
	 * Increments cryptograms played When a player plays a new cryptogram, this
	 * should be incremented
	 */
	public void incrementCryptogramsPlayed() {
		cryptogramsPlayed++;
	}

	/*
	 * Gets the current players accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/*
	 * Gets the player's total number of completed cryptograms
	 */
	public int getNumCryptogramsCompleted() {
		return cryptogramsCompleted;
	}

	/*
	 * Gets the player's total number of played cryptograms
	 */
	public int getNumCryptogramsPlayed() {
		return cryptogramsPlayed;
	}

	public void incrementNumGuesses() {
		totalGuesses++;
	}

	public void incrementNumCorrectGuesses() {
		numCorrectGuesses++;
	}

	public void setUsername(String name) {
		username = name;
	}

	public void setTotalGuesses(int totG) {
		totalGuesses = totG;
	}

	public void setCorrectGuesses(int corG) {
		numCorrectGuesses = corG;
	}

	public void setCryptogramsPlayed(int played) {
		cryptogramsPlayed = played;
	}

	public void setCryptogramsCompleted(int completed) {
		cryptogramsCompleted = completed;
	}

	public void setAccuracy(double acc) {
		accuracy = acc;
	}

	public String getUsername() {
		return username;
	}

	public int getNumCorrectGuesses() {
		return numCorrectGuesses;
	}

	public int getTotalGuesses() {
		return totalGuesses;
	}

	@Override
	public int compareTo(Player p) {
		return Integer.compare( p.getNumCryptogramsCompleted(),this.cryptogramsCompleted);
				
		
	}

}
