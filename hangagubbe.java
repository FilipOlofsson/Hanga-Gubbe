import java.util.Random;
import java.util.Scanner;


public class hangagubbe {
	
	static String[] Wordlist = {
			"JAJA",
			"LALA",
			"FAFA",
			"GAGA"
	};
	static boolean playing = true;
	
	static Random rng = new Random();
	static Scanner scan = new Scanner(System.in);
	
	static char[] randomWord = Wordlist[rng.nextInt(Wordlist.length)].toCharArray();
	static char[] foundLetters = new char[randomWord.length];
	
	static int q = 1;
	static int lettersFound;
	static int Lives = 8;
	
	static String userGuess;
	
	public static void main(String[] args) {
		for(int i = 0; i < foundLetters.length; i++) {
			foundLetters[i] = '_';
		}
		while(Lives > 0 && playing) {
			
			/*
			 * Printa information
			 */
			System.out.println("Lives: " + Lives);
			if(foundLetters[0] != ' ') {
				for(int i = 0; i < foundLetters.length; i++) {
					System.out.print(foundLetters[i] + " ");
				}
			}
			System.out.println("\n");
			/*
			 * Slut på printa information.
			 */
			userGuess = scan.nextLine();
			if(userGuess.length() == 1) {
				for(int i = 0; i < randomWord.length; i++) {
					if(userGuess.charAt(0) == randomWord[i]) {
						foundLetters[i] = randomWord[i];
					}
				}
			} else {
				if(userGuess.equalsIgnoreCase(String.valueOf(randomWord))) {
					playing = false;
					System.out.println("You win!");
				} else {
					Lives--;
				}
			}
		}
	}
}
