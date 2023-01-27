import java.util.HashMap;
import java.util.Scanner;

/*
*  Cryptogram class
*  Main interface for cryptograms
*/

public class Cryptogram<E> {

	public String phrase; // current phrase loaded from file
	public HashMap<Character, E> cryptogramAlphabet; // the solution, what each character is mapped to
	public HashMap<Character, Integer> frequencies = new HashMap<>(); // holds the frequencies of each mapped later
	public HashMap<Character, Double> english = new HashMap<>();
	/*
	 * Gets the frequencies of each letter in the puzzle
	 */
	public HashMap<Character, Integer> getFrequencies() {
		for (char c : phrase.toLowerCase().replaceAll("[$&+,:;=?@#|'<>.-^*()%!\\s]", "").toCharArray()) {
			if (frequencies.containsKey(c)) {
				frequencies.put(c, frequencies.get(c) + 1);
			} 
			else {
				frequencies.put(c, 1);
			}
		}
		return frequencies;
	}

	public void enterLetter(String key, char value, HashMap<Object, Character> mapping) {
		// Overridden
	}

	public void printMap() {
		for (char c : phrase.toLowerCase().toCharArray()) {
			if (cryptogramAlphabet.get(c) == null) {
				System.out.print(' ');
			} 
			else if (cryptogramAlphabet.get(c).toString().length() <= 1) {
				System.out.print("|" + cryptogramAlphabet.get(c) + " |");
			} 
			else {
				System.out.print("|" + cryptogramAlphabet.get(c) + "|");
			}
		}
		System.out.println(" ");
	}

	@SuppressWarnings("resource")
	public int handleDuplication(String key, char value, HashMap<Object, Character> mapping, int statement) {
		Scanner scan = new Scanner(System.in);
		if (statement == 1) {
			System.out.print("Key already mapped. Do you want to overwrite with " + value + "?: (y/n) ");
			if (scan.next().equals("y")) {
				mapping.put(key.charAt(0), value);
				return 1;
			} 
			else {
				return 1;
			}

		} 
		else {
			System.out.print("Key already mapped. Do you want to overwrite with " + value + "?: (y/n)");
			if (scan.next().equals("y")) {
				mapping.put(Integer.parseInt(key), value);
				return 1;
			} 
			else {
				// TODO
			}
		}
		
		scan.close();
		return 0;
	}

	public boolean keyCheck(String key, HashMap<Object, Character> mapping, int statement) {
		if (statement == 1) {
			if (mapping.containsKey(key.charAt(0))) {
				return true;
			}
		} 
		else if (statement == 2) {
			if (mapping.containsKey(Integer.valueOf(key))) {
				return true;
			}
		}
		return false;
	}

	public void remove(String key, HashMap<Object, Character> mapping) {
		// Overridden
	}

	public boolean alreadyMapped(char c, HashMap<Object, Character> mapping) {
		return (mapping.containsValue(c));
	}

	public boolean checkGuess(String key, char value) {
		return true;
	}

	public void fillAlphabet(String string) {
		// TODO
	}
	
	public void fill() {
		english.put('e', 11.16);
		english.put('a', 8.49);
		english.put('r', 7.58);
		english.put('i', 7.54);
		english.put('o', 7.16);
		english.put('t', 6.95);
		english.put('n', 6.65);
		english.put('s', 5.73);
		english.put('l', 5.48);
		english.put('c', 4.53);
		english.put('u', 3.63);
		english.put('d', 3.38);
		english.put('p', 3.16);
		english.put('m', 3.01);
		english.put('h', 3.00);
		english.put('g', 2.47);
		english.put('b', 2.07);
		english.put('f', 1.81);
		english.put('y', 1.77);
		english.put('w', 1.28);
		english.put('k', 1.10);
		english.put('v', 1.00);
		english.put('x', 0.29);
		english.put('z', 0.27);
		english.put('j', 0.19);
		english.put('q', 0.19);
	}

}