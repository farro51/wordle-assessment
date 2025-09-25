import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Wordle {

    private static String[] feedback;
    private static final int MAX_GUESSES = 5;
    private static final String WORDS_FILE_PATH = "src/main/resources/words.txt";
    private static final String GAME_RULES = """
            Welcome to Wordle!
            You have 5 guesses to find a five-letter english word.
            Letters in the right position will have a green highlight.
            Letters in the word but in the wrong position will have a yellow highlight.
            The yellow highlight will not show up if you have more of a letter than in the correct
            answer.
            """;

    public static void main(String[] args) {
        try{
            Wordle.startGame(getWordsFromFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startGame(List<String> words) {
        Scanner scanner = new Scanner(System.in);

        int wordIndex = new Random().nextInt(words.size());
        String currentWord = words.get(wordIndex).trim().toUpperCase(Locale.ENGLISH);
        System.out.println(GAME_RULES);
        for (int i = MAX_GUESSES; i > 0; i--) {
            System.out.println(i + " guesses remaining");
            System.out.println("Enter a new five-letter word:");
            String guess = scanner.nextLine().trim().toUpperCase(Locale.ENGLISH);
            if (currentWord.equals(guess)) {
                System.out.println("Perfect. You guess it!");
                break;
            }
            if (guess.length() != 5) {
                System.out.println("Only five-letters words allowed");
                break;
            }

            System.out.println(highlightWord(guess, currentWord) + ColorCode.WHITE.getCode());

            if (i == 1) System.out.println("Nice try, the word was: " + currentWord);
        }
        scanner.close();
    }

    public static String highlightWord(String guess, String currentWord) {
        feedback = new String[currentWord.length()];
        highlightGreen(guess, currentWord);
        for (int j = 0; j < 5; j++) {
            if (feedback[j] == null) {
                String letter = String.valueOf(guess.charAt(j));
                if (!currentWord.contains(letter)) {
                    feedback[j] = ColorCode.WHITE.getCode() + letter;
                    continue;
                }
                highlightYellow(letter, currentWord, guess);
            }
        }
        return String.join("", feedback);
    }

    private static List<String> getWordsFromFile() throws IOException {
        return Files.readAllLines(
                Paths.get(WORDS_FILE_PATH));
    }

    private static int getOccurrences(String letter, String word) {
        return word.length() - word.replace(String.valueOf(letter), "").length();
    }

    private static void highlightGreen(String guess, String word) {
        for (int i = 0; i < 5; i++) {
            String letter = String.valueOf(guess.charAt(i));
            if (guess.charAt(i) == word.charAt(i)) {
                feedback[i] = ColorCode.GREEN.getCode() + letter;
            }
        }
    }

    private static void highlightYellow(String letter, String word, String guess) {
        int letterOccurrences = getOccurrences(letter, word);
        int fromIndex = guess.indexOf(letter);
        int highlighted = (int) Arrays.stream(feedback)
                .filter(l -> l != null && l.equals(ColorCode.GREEN.getCode() + letter))
                .count();
        while (fromIndex != -1) {
            if (feedback[fromIndex] == null) {
                feedback[fromIndex] =  (highlighted < letterOccurrences ?
                        ColorCode.YELLOW.getCode() : ColorCode.WHITE.getCode()) + letter;
                highlighted++;
            }
            fromIndex = guess.indexOf(letter, fromIndex + 1);
        }
    }
}