import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ResultReporter {

    public static void report(Map<String, AtomicLong> wordCount) {
        writeResultReport(sortedWordCount(wordCount));
    }

    private static Collection<WordCountEntry> sortedWordCount(Map<String, AtomicLong> wordCount) {

        System.out.printf("sorting %d entries...%n", wordCount.size());

        SortedSet<WordCountEntry> wordCountEntries = new TreeSet<>(
                Comparator.<WordCountEntry>comparingLong(entry -> entry.count).reversed()
        );

        wordCount.entrySet().stream()
                .map(stringAtomicLongEntry ->
                        new WordCountEntry(
                                stringAtomicLongEntry.getKey(),
                                stringAtomicLongEntry.getValue().get()
                        )
                ).forEach(wordCountEntries::add);

        System.out.println("sorting done!");

        return wordCountEntries;
    }

    private static void writeResultReport(Collection<WordCountEntry> entries) {

        System.out.println("writing result...");

        try {

            FileWriter writerWordWithCount = new FileWriter("sorted_words_with_count.txt");
            FileWriter writerOnlyWords = new FileWriter("sorted_words.txt");

            for (WordCountEntry entry : entries) {

                if (shouldBeIgnored(entry)) {
                    continue;
                }

                writerWordWithCount.write(String.format("%s %d%n", entry.getWord(), entry.getCount()));
                writerOnlyWords.write(entry.word);
                writerOnlyWords.write("\n");
            }

            writerWordWithCount.close();
            writerOnlyWords.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean shouldBeIgnored(WordCountEntry entry) {
        return entry.word.length() < 2;
    }

    private static class WordCountEntry {

        private final String word;
        private final long count;

        private WordCountEntry(String word, long count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public long getCount() {
            return count;
        }
    }
}
