import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class hängagubbe {

    private static char Word[];

    private static boolean playing = true;

    private static Random rng = new Random();
    private static Scanner scan = new Scanner(System.in);

    private static char[] foundLetters;
    private static char[] lettersGuessed = new char[27];
    private static int lettersGussedCounter = 0;

    private static int Lives = 8;

    public static void main(String[] args) throws IOException {

        Word = wordlist().toCharArray();
        foundLetters = new char[Word.length];

        /*
        Replace alla bokstäver i character arrayen foundLetters till _
         */
        for(int i = 0; i < foundLetters.length; i++) {
            foundLetters[i] = '_';
        }

        /*
        Konverterar alla bokstäver i strängen Word till lower case.
         */
       for(int i = 0; i < Word.length; i++) {
           Word[i] = Character.toLowerCase(Word[i]);
       }

       /*
       Konverterar alla bokstäver i character arrayen letterGuessed till ett mellanslag.
        */
        for(int i = 0; i < 27; i++) {
            lettersGuessed[i] = ' ';
        }


        //Nu kör vi spelet.
        Play();
    }

    static void Play() {
        while(Lives > 0 && playing) {

			/*
			 * Printa information
			 */
            System.out.println("Lives: " + Lives);
            System.out.println("Letters guessed: ");
            for(int i = 0; i < lettersGussedCounter; i++) {
                System.out.print(lettersGuessed[i] + ", ");
            }
            System.out.print("\n");
                for(int i = 0; i < foundLetters.length; i++) {
                    System.out.print(foundLetters[i] + " ");
                }
            System.out.println("\n");
			/*
			 Slut på printa information.
			 */
            String userGuess  = scan.nextLine(); //Användaren skriver in en sträng.
            if(userGuess.length() == 1) { //Det kollas om användaren vill gissa på en bokstav eller ett ord


                if(!String.valueOf(lettersGuessed).contains(userGuess)) {
                    lettersGuessed[lettersGussedCounter] = userGuess.charAt(0);
                    lettersGussedCounter++;
                }

                //Loopar igenom hela randomWord för att kolla ifall användarens input matchar någon bokstav i ordet.
                //Om det matchar så byts understräcket ut mot bokstaven som ska vara där.
                for(int i = 0; i < Word.length; i++) {
                    if(userGuess.charAt(0) == Word[i]) {
                        foundLetters[i] = Word[i];
                    }
                }

                //Kollar ifall användarens input finns i randomWord, om det inte finns så tappar han ett liv.
                if (!new String(Word).contains(userGuess)) {
                    Lives--;
                }
            } else { //Användaren har gissat på ett ord.
                if(userGuess.equalsIgnoreCase(String.valueOf(Word))) { //Om användarens ord matchar randomWord utan att caps spelar roll.
                    playing = false; //playing sätts till false för att avbryta while-loopen.
                    System.out.println("You win!");
                } else { //Om användarens ord inte matchar randomWord så tappar han ett liv.
                    Lives--;
                }
            }
        }
        if(Lives < 1 && playing) {
            System.out.println("You lose. The word was " + String.valueOf(Word));
        }
    }

    private static String wordlist() throws IOException {
            File dictionary = new File("wordlist.dict");

            FileReader fileReader = new FileReader(dictionary);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


            int nbrLines = countLines(dictionary);

            String[] wholeList = new String[nbrLines];

            for(int i = 0; i < nbrLines; i++) {
                wholeList[i] = bufferedReader.readLine();
            }

            return wholeList[rng.nextInt(nbrLines)];
    }

    private static int countLines(File file) throws IOException {
        int lines = 0;

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[8 * 1024];
        int read;

        while ((read = fis.read(buffer)) != -1) {
            for (int i = 0; i < read; i++) {
                if (buffer[i] == '\n') lines++;
            }
        }

        fis.close();

        return lines;
    }

}