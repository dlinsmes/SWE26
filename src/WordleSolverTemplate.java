import java.util.ArrayList;
import java.util.Scanner;

public class WordleSolverTemplate {

    private final static int GREEN = 2;
    private final static int YELLOW = 1;
    private final static int GREY = 0;
    private final static String [] feedbackSymbols = {"-", "?", "+"};

    public static ArrayList<String> validGuesses = new ArrayList<String>();
    private static String answer;


    //change these values as you test
    private static int reps = 1;
    private static boolean verbose = true;
    private static boolean wait = true;


    //leave - don't need to add or change anything in main()
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        double nGuessAvg = 0;
        for (int i = 0; i < reps; i++) {
            generateAnswerAndWordList();

            if (verbose) {
                System.out.println();
                if (reps > 1)
                    System.out.println("Round " + (i + 1));
                System.out.println("answer: " + answer);
                System.out.println("valid guess words: " + validGuesses.size());
            }

            String guess = "";
            int guessCount = 0;
            while (!guess.equals(answer)) {
                guessCount++;
                guess = validGuesses.get((int) (Math.random() * validGuesses.size()));

                int [] results = provideFeedback(guess);
                filterGuessList(guess, results);

                if (verbose) {
                    System.out.println("Guess " + guessCount + ": " + guess);
                    System.out.print("         ");
                    for (int val: results)
                        System.out.print(feedbackSymbols[val]);
                    System.out.println();
                    if (!guess.equals(answer)) {
                        System.out.println("valid guess words remaining: " + validGuesses.size());
                        if (wait) {
                            System.out.println(("press enter to continue"));
                            s.nextLine();
                        }
                    }
                }
            }
            nGuessAvg = ((nGuessAvg * i) + guessCount ) / (i+1);
        }

        System.out.println();

        if (reps > 1)
            System.out.println("Avg guesses over " + reps + " rounds: " + nGuessAvg);

    }

    //generate random valid answer and compile valid guess list
    public static void generateAnswerAndWordList() {
        answer = "stale";               //sample
        validGuesses.add("apple");      //sample
    }

    //return 5-length int array representing the colors that correspond to each guess letter
    //-see final variables for color-value mapping
    public static int[] provideFeedback(String guess) {
        //use char data type instead of substrings
        char [] letters = guess.toCharArray();

        //use == and != for char data type equality
        if (letters[1] == letters[2]);  //sample


        int [] feedback = new int [5];

        feedback[0] = YELLOW;   //sample
        feedback[1] = GREY;     //sample
        feedback[2] = GREY;     //sample
        feedback[3] = GREEN;    //sample
        feedback[4] = GREEN;    //sample

        return feedback;
    }

    //remove values from validGuesses list as determined by guess and feedback
    public static void filterGuessList(String guess, int [] feedback) {
        //use char data type instead of substrings
        char [] letters = guess.toCharArray();

        //use == and != for char data type equality
        if (letters[1] == letters[2]);  //sample

        if (feedback[1] == GREY);       //sample

    }


}