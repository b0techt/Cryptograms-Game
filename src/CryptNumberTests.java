import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CryptNumberTests {
	Game g;
	Player p;
//	
//	@BeforeEach
//	public void beforeEach() throws FileNotFoundException {
//		p = new Player("Emmanuel");
//		g = new Game(p, "number");
//
//	}
//	
//	@Test
//	public void generatenumberCryptogram() throws FileNotFoundException {
//		assertTrue(g.numberCrypt != null);
//	}
//	
//	//tests if each char has a key by turning phrase into chars only, checking if each char has its own key, and counts. At the end it compares count to the ammount of characters.
//	@Test
//	public void mapLetters() {
//		int count = 0;
//		for(char c : g.numberCrypt.phrase.toLowerCase().replaceAll("[$&+,:;=?@#|'<>.-^*()%!\\s]", "").toCharArray()) {
//			if(g.numberCrypt.cryptogramAlphabet.containsKey(c)) {
//				count ++;
//			}
//		}
//		assertEquals(count, g.numberCrypt.phrase.toLowerCase().replaceAll("[$&+,:;=?@#|'<>.-^*()%!\\s]", "").toCharArray().length);
//	}
//	
//	@Test
//	public void testFinish() {
//		for(char c : g.numberCrypt.cryptogramAlphabet.keySet()) {
//			g.numberMap.put(g.numberCrypt.cryptogramAlphabet.get(c), c);
//		}
//		g.numberMap.remove(g.numberMap.keySet().toArray()[0]);
//		g.updateNumber();
//		g.numberCrypt.printMap();
//		g.enterLetter();
//		assertEquals(g.finished , true);
//		
//	}
//	
//	@Test
//	public void testFinishFalse() {
//		for(char c : g.numberCrypt.cryptogramAlphabet.keySet()) {
//			g.numberMap.put(g.numberCrypt.cryptogramAlphabet.get(c), c);
//		}
//		g.numberMap.remove(g.numberMap.keySet().toArray()[0]);
//		g.updateNumber();
//		g.numberCrypt.printMap();
//		g.enterLetter();
//		assertFalse(g.finished);
//		
//	}
//	
//	@Test
//	public void testUnusedKey() {
//		int sizeBefore = g.numberMap.size();
//		g.enterLetter();
//		assertEquals(sizeBefore, g.numberMap.size());
//	}
//	
//	@Test
//	public void testUndoSuccessful() {
//		for(char c : g.numberCrypt.cryptogramAlphabet.keySet()) {
//			g.numberMap.put(g.numberCrypt.cryptogramAlphabet.get(c), c);
//		}
//		g.numberMap.remove(g.numberMap.keySet().toArray()[0]);
//		g.updateNumber();
//		g.numberCrypt.printMap();
//		int sizeBefore = g.numberMap.size();
//		g.enterLetter();
//		assertTrue(sizeBefore > g.numberMap.size());
//		
//	}
//	
//	@Test
//	public void testUndoUnsuccessful() {
//		for(char c : g.numberCrypt.cryptogramAlphabet.keySet()) {
//			g.numberMap.put(g.numberCrypt.cryptogramAlphabet.get(c), c);
//		}
//		g.numberMap.remove(g.numberMap.keySet().toArray()[0]);
//		g.updateNumber();
//		g.numberCrypt.printMap();
//		int sizeBefore = g.numberMap.size();
//		g.enterLetter();
//		assertTrue(sizeBefore == g.numberMap.size());
//	}
//	
//	@Test
//	public void testAlreadyUsed(){
//		for(char c : g.numberCrypt.cryptogramAlphabet.keySet()) {
//			g.numberMap.put(g.numberCrypt.cryptogramAlphabet.get(c), c);
//		}
//		g.numberMap.remove(g.numberMap.keySet().toArray()[0]);
//		g.updateNumber();
//		g.numberCrypt.printMap();
//		int sizeBefore = g.numberMap.size();
//		g.enterLetter();
//		assertTrue(sizeBefore == g.numberMap.size());
//	}
//	
//	
	
}
