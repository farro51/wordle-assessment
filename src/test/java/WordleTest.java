import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleTest {

    @Test
    void shouldHighlightLettersFound() {
        List<String> guessedWords = List.of("OTTER", "WASTE");
        List<String> expectedResults = List.of(ColorCode.WHITE.getCode() + "O"
                        + ColorCode.WHITE.getCode() + "T"
                        + ColorCode.GREEN.getCode() + "T"
                        + ColorCode.GREEN.getCode() + "E"
                        + ColorCode.GREEN.getCode() + "R",
                ColorCode.GREEN.getCode() + "W"
                        + ColorCode.GREEN.getCode() + "A"
                        + ColorCode.WHITE.getCode() + "S"
                        + ColorCode.YELLOW.getCode() + "T"
                        + ColorCode.YELLOW.getCode() + "E");
        for (int i = 0; i < guessedWords.size(); i++) {
            String result = Wordle.highlightWord(guessedWords.get(i), "WATER");
            assertEquals(expectedResults.get(i), result);
        }
    }
}