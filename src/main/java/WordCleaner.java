import java.util.Locale;

public class WordCleaner {

    public static String clean(String input) {
        return input
                .toLowerCase(Locale.GERMAN)
                .replaceAll("[^a-zäöü\\-_]", "");
    }
}
