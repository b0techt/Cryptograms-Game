import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Executable;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.function.ThrowingRunnable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class CryptLetterTests {
	Game g;
	Player p;
	PrintWriter pw;
	PrintWriter pw2;
	Scanner scan;
	Scanner playerScan;
	Players players;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@BeforeEach
	public void beforeEach() throws IOException {
		p = new Player("Emmanuel");
		pw = new PrintWriter("cryptogram_state.txt");
		pw2 = new PrintWriter("players.txt");
		g = new Game(p, "letter");
		pw.write("");
		pw2.write("");
		scan = new Scanner(new File("cryptogram_state.txt"));
		playerScan = new Scanner(new File("players.txt"));
		players = new Players();

	}
	
	//Tests that the solution has been displayed
	@Test
	public void displaySolution() throws IOException {
		
		g.playGame();
		assertEquals(g.showSolution , 1);
        
	}
	
	@Test
	public void displayFrequencies() throws IOException {
		
		g.playGame();
		assertEquals(g.displayFreq , 1);
        
	}
	
	@Test
	public void displayStats() throws IOException {
		Main.players.addPlayer(p);
		//Main.players.updatePlayerArray(p);
		Main.players.savePlayersToFile();
		g.playGame();
		assertEquals(g.displayStats , 1);
        
	}
	
	@Test
	public void displayStatsError() throws IOException {
		
		g.playGame();
		assertEquals(g.displayStats , 0);
        
	}
	@Test
	public void displayHintEmpty() throws IOException {
		
		g.playGame();
		assertEquals(g.hinted , 1);
        
	}
	
	@Test
	public void displayHintFull() throws IOException {
		
		g.playGame();
		assertEquals(g.hinted , 1);
        
	}
	
	
}
