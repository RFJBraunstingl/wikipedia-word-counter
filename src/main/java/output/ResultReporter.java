package output;

import model.WordCountEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ResultReporter {

    public static void report(Map<String, AtomicLong> wordCount) {
        writeResultReport(WordCountSorter.sortedWordCount(wordCount));
    }

    private static void writeResultReport(Collection<WordCountEntry> entries) {

        System.out.println("writing result...");

        try {

            FileWriter writerWordWithCount = new FileWriter("sorted_words_with_count.txt");
            FileWriter writerOnlyWords = new FileWriter("sorted_words.txt");

            long ignoredCounter = 0;

            for (WordCountEntry entry : entries) {

                if (shouldBeIgnored(entry)) {
                    ignoredCounter++;
                    continue;
                }

                writerWordWithCount.write(String.format("%s %d%n", entry.getWord(), entry.getCount()));
                writerOnlyWords.write(entry.getWord());
                writerOnlyWords.write("\n");
            }

            writerWordWithCount.close();
            writerOnlyWords.close();

            System.out.printf("%d entries ignored!%n", ignoredCounter);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean shouldBeIgnored(WordCountEntry entry) {

        String word = entry.getWord();
        long count = entry.getCount();
        int length = word.length();

        // very short words
        return length < 2 ||
                /* everything with less than 4 occurrences is probably an accident */
                count < 4 ||
                /* HTML tags */
                word.contains("style") ||
                word.contains("span") ||
                /* 3 letter words should only be included when they have been used significantly */
                (length < 4 && count < 20_000);
    }

}
