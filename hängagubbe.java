import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class hängagubbe {

    private static char Word[]; //Ordet som man gissar på.

    private static boolean playing = true; //True om man fortfarande spelar. False om man vunnit.

    private static Random rng = new Random(); //Används för slumpning, kunde använt Math.random också
    private static Scanner scan = new Scanner(System.in); //Scanner, ganska uppenbart.

    private static char[] foundLetters; //Array med alla bokstäver man hittat
    private static char[] lettersGuessed = new char[26]; //Antal bokstäver man gissat på, 26 för att man bara kan gissa på engelska bokstäver.
    private static int lettersGussedCounter = 0; //Troligtvis onödig men det fungerar på det här sättet.

    private static int Lives = 8; //Antal liv man har.

    public static void main(String[] args) throws IOException {

        /*
         * Hämta ordet från wordlisten och gör om ordet till en character array.
         */
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
        for(int i = 0; i < 26; i++) {
            lettersGuessed[i] = ' ';
        }


        //Nu kör vi spelet.
        Play();
    }

    private static void Play() {
        while(Lives > 0 && playing) { //While-loop som körs medans man har över 0 liv och inte vunnit.

			/*
			 * Printa information
			 */
            System.out.println("Lives: " + Lives);
            System.out.println("Letters guessed: ");
            for(int i = 0; i < lettersGussedCounter; i++) {
                System.out.print(lettersGuessed[i] + ", ");
            }
            System.out.print("\n");
                for(char i : foundLetters) {
                    System.out.print(i + " ");
                }
            System.out.println("\n");
			/*
			 Slut på printa information.
			 */
            String userGuess  = scan.nextLine(); //Användaren skriver in en sträng.
            if(userGuess.length() == 1) { //Det kollas om användaren vill gissa på en bokstav eller ett ord

                /*
                 * Lägg till bokstäverna i en array som innehåller alla bokstäver som är gissade.
                 */
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
                } else if(userGuess.equalsIgnoreCase("give me max lives")) { //Lite fuskkoder.
                    System.out.println("Cheater. You've been given max lives.");
                    Lives = 2147483647; //Sätt liv till 2147483647
                    System.out.print("\n");
                } else if(userGuess.equalsIgnoreCase("give me the word")) { //Lite fuskkoder.
                    System.out.println("Cheater. The word is: "+String.valueOf(Word)); //Printa ordet.
                    System.out.print("\n");
                } else { //Om användarens ord inte matchar randomWord så tappar han ett liv.
                    Lives--;
                }
            }
        }
        if(Lives <= 0 && playing) { //Om spelet är slut. playing är bara false om man vunnit.
            System.out.println("You lose. The word was " + String.valueOf(Word));
        }
    }

    /*
     * Metod som importerar ett ord från en extern wordlist. Den är ganska oeffektiv eftersom att läser in hela filen och sedan slumpar den ett ord från det som är inläst.
     * Den skulle kunna slumpa ett nummer och sedan bara läsa fram tills det och ta det ordet.
     */
    private static String wordlist() throws IOException {
            File dictionary = new File("wordlist.dict"); //Definera vilken fil som ska läsas in.

            FileReader fileReader = new FileReader(dictionary);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


            int nbrLines = countLines(dictionary); //Sätt nbrLines till antalet rader i filen. Anväder metoden countLines för detta.

            String[] wholeList = new String[nbrLines]; //Gör en String array som ska innehålla alla rader i filen.

            for(int i = 0; i < nbrLines; i++) {
                wholeList[i] = bufferedReader.readLine(); //Fyll String arrayen med wordlisten, rad för rad.
            }

            return wholeList[rng.nextInt(nbrLines)]; //Returnera hela arrayen..
    }

    /*
     * Metod som räknar raderna i en fil.
     */

    private static int countLines(File file) throws IOException {
        int lines = 0;

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[8192]; //8192 bytes med data per rad.
        int read;

        while ((read = fileInputStream.read(buffer)) != -1) { //Körs medans det finns rader kvar att läsas.
            for (int i = 0; i < read; i++) { //read är antalet bokstäver i raden. Loopa medans det finns olästa bokstäver i raden.
                if (buffer[i] == '\n') lines++; //Om byten som läses är \n så läggs det till en i integern lines.
            }
        }

        fileInputStream.close(); //Stäng strömmen

        return lines; //Returnera antalet rader i filen som skickades till metoden.
    }

}
