package text;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TextTransformerTest {

    private void assertTransformation(String input, String... expected) {
        Assert.assertArrayEquals(
                expected,
                TextTransformer.getCleanedWords(input).toArray()
        );
    }

    @Test
    public void validInputString_isSplitAtWhitespaces() {
        String input = "abc def ghi";
        assertTransformation(
                input,
                "abc",
                "def",
                "ghi"
        );
    }

    @Test
    public void inputWithSpecialCharacters_isCleanedAppropriately() {
        String input = "[abc] 'def' g_hi";
        assertTransformation(
                input,
                "abc",
                "def",
                "ghi"
        );
    }

    @Test
    public void uppercaseText_isTransformedToLowercase() {
        String input = "ABC DEF GHI";
        assertTransformation(
                input,
                "abc",
                "def",
                "ghi"
        );
    }

    @Test
    public void numbers_areIgnored() {
        String input = "a1b 123 ghi";
        assertTransformation(
                input,
                "ab",
                "ghi"
        );
    }

    @Test
    public void actualScenario_isHandledAsExpected() {
        String input = "'''Alan Smithee''' steht als [[Pseudonym]] für einen fiktiven Regisseur, der Filme verantwortet, bei denen der eigentliche [[Regisseur]] seinen Namen nicht mit dem Werk in Verbindung gebracht haben möchte. Von 1968 bis 2000 wurde es von der [[Directors Guild of America]] (DGA) für solche Situationen empfohlen";
        assertTransformation(
                input,
                "alan",
                "smithee",
                "steht",
                "als",
                "pseudonym",
                "für",
                "einen",
                "fiktiven",
                "regisseur",
                "der",
                "filme",
                "verantwortet",
                "bei",
                "denen",
                "der",
                "eigentliche",
                "regisseur",
                "seinen",
                "namen",
                "nicht",
                "mit",
                "dem",
                "werk",
                "in",
                "verbindung",
                "gebracht",
                "haben",
                "möchte",
                "von",
                "bis",
                "wurde",
                "es",
                "von",
                "der",
                "directors",
                "guild",
                "of",
                "america",
                "dga",
                "für",
                "solche",
                "situationen",
                "empfohlen"
        );
    }
}
