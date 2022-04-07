package text;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class TextTransformer {

    public static Collection<String> getCleanedWords(String input) {
        return Arrays.stream(input.split("\\s"))
                .map(WordCleaner::clean)
                .filter(TextTransformer::isNotBlank)
                .collect(Collectors.toList());
    }

    private static boolean isNotBlank(String s) {
        return s != null &&
                s.trim().length() > 0;
    }
}
