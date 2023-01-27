import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
* NumberCryptogram class
* Extends Cryptogram
*/

public class NumberCryptogram extends Cryptogram<Integer> {

	private int randomLine;

	public NumberCryptogram(String file) throws FileNotFoundException {
		cryptogramAlphabet = new HashMap<>();
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			Random rand = new Random();
			randomLine = rand.nextInt(15) + 1;
			String emptyCryptogram;
			ArrayList<Integer> arr = new ArrayList<>(26 + 1); // stores numbers 1-26
			int i = 0;
			boolean assigned;

			for (int x = 1; x <= 26; x++) {		// populates the possible numbers in the array
				arr.add(x);
			}

			while (i < (randomLine)) {
				this.phrase = s.nextLine();
				i++;
			}

			if (phrase == null) {
				System.out.println("No phrases exist in the file. Exiting...");
				System.exit(0);
			}

			for (char c : phrase.toLowerCase().replaceAll("[$&+,:;=?@#|'<>.-^*()%!\\s]", "").toCharArray()) {
				if (!cryptogramAlphabet.containsKey(c)) {
					assigned = false;
					while (!assigned) {
						i = rand.nextInt(arr.size());
						if (arr.get(i) != c && arr.get(i) != 0) {
							cryptogramAlphabet.put(c, arr.get(i));
							arr.set(i, 0);
							assigned = true;
						}
					}
				}
			}
			emptyCryptogram = phrase;
			emptyCryptogram = emptyCryptogram.replaceAll("[a-zA-Z]", "|__|");

			System.out.println(emptyCryptogram); // prints the cryptogram in an empty state

			printMap();
			s.close();
		} 
		catch (FileNotFoundException fException) {
			System.out.println("File not found.");
			fException.printStackTrace();
		}
	}

	public NumberCryptogram() {
		cryptogramAlphabet = new HashMap<>();
		phrase = "";
	}

	/*
	 * Gets a plain letter, based on the integer value mapped
	 */
	public int getPlainLetter(int cryptoValue) {
		// TODO change return type and add body
		return 0;
	}

	public void enterLetter(String key, char value, HashMap<Object, Character> mapping) {
		if (!alreadyMapped(value, mapping)) {
			if (cryptogramAlphabet.containsValue(Integer.parseInt(key))) {
				if (keyCheck(key, mapping, 0)) {
					int returned = handleDuplication(key, value, mapping, 1);
					// System.out.println(returned);
					if (returned == 1) {
						return;
					}
				} 
				else {
					if (key.length() >= 3) {
						System.out.println("The key you entered is too long");
					} 
					else {
						mapping.put(Integer.parseInt(key), value);
					}
				}
			} 
			else {
				System.out.println("The key cannot be found");
			}
		} 
		else {
			System.out.println("Value already mapped, please undo first!");
		}
	}

	@Override
	public void remove(String key, HashMap<Object, Character> mapping) {
		mapping.remove(Integer.parseInt(key));
	}

	@Override
	public boolean checkGuess(String key, char value) {
		if (!cryptogramAlphabet.containsKey(value)) {
			return false;
		} 
		else if (Integer.parseInt(key) == cryptogramAlphabet.get(value)) {
			return true;
		}

		return false;
	}

	public int getRandLine() {
		return randomLine;
	}
	
	@Override
	public void fillAlphabet(String string) {
		int val = 0;	// mid[1] needs to be parsed
		
        for(String sub : string.split("\\|")) {
            String[] mid = sub.split("=");
            if (mid.length > 1) {
            	val = Integer.parseInt(mid[1]);
                cryptogramAlphabet.put(mid[0].charAt(0), val);
                
            }
        }
    }
}
