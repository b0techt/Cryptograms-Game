import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
* LetterCryptogram class
* Extends Cryptogram
*/

public class LetterCryptogram extends Cryptogram<Character> {

	public String emptyCryptogram;
	private int randomLine;

	public LetterCryptogram(String file) throws FileNotFoundException {
		cryptogramAlphabet = new HashMap<>();
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			Random rand = new Random();
			randomLine = rand.nextInt(15) + 1;
			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			int i = 0;
			boolean assigned;

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
						i = rand.nextInt(alphabet.length);
						if (alphabet[i] != c && alphabet[i] != '!') {
							cryptogramAlphabet.put(c, alphabet[i]);
							alphabet[i] = '!';
							assigned = true;
						}
					}
				}
			}

			emptyCryptogram = phrase;
			emptyCryptogram = emptyCryptogram.replaceAll("[a-zA-Z]", "|__|");

			System.out.println(emptyCryptogram);	// prints the cryptogram in an empty state

			printMap();
			s.close();
		} 
		catch (FileNotFoundException fException) {
			System.out.println("File not found.");
			fException.printStackTrace();
		}
	}

	public LetterCryptogram() {
		cryptogramAlphabet = new HashMap<>();
		phrase = "";
	}

	/*
	 * Gets a plain letter based on the mapped character
	 */
	public char getPlainLetter(char cryptoLetter) {
		// TODO change return type and add body
		return 0;
	}

	@Override
	public void enterLetter(String key, char value, HashMap<Object, Character> mapping) {
		if (!alreadyMapped(value, mapping)) {
			if (cryptogramAlphabet.containsValue(key.charAt(0))) {
				if (keyCheck(key, mapping, 1)) {
					int returned = handleDuplication(key, value, mapping, 1);
					if (returned == 1) {
						return;
					}
				} 
				else {
					mapping.put(key.charAt(0), value);
				}
			} 
			else {
				System.out.println("\nThe key cannot be found, no change to mapping.");
			}
		}
		else {
			System.out.println("\nValue already mapped, please undo first!\n");
		}
	}

	@Override
	public void remove(String key, HashMap<Object, Character> mapping) {
		mapping.remove(key.charAt(0));
	}

	@Override
	public boolean checkGuess(String key, char value) {
		if (!cryptogramAlphabet.containsKey(value)) {
			return false;
		} 
		else if (key.charAt(0) == cryptogramAlphabet.get(value)) {
			return true;
		}

		return false;
	}

	public int getRandLine() {
		return randomLine;
	}

	@Override
	public void fillAlphabet(String string) {
        for(String sub : string.split("\\|")) {
            String[] mid = sub.split("=");
            if (mid.length > 1) {
                cryptogramAlphabet.put(mid[0].charAt(0), mid[1].charAt(0));
            }
        }
    }

}
