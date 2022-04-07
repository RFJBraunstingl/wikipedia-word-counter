package output;

import model.WordCountEntry;
import model.WordCountEntryComparator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class WordCountSorter {

    private static final int STEP_SIZE_FOR_LOGGING = 100_000;

    static Collection<WordCountEntry> sortedWordCount(Map<String, AtomicLong> wordCount) {

        System.out.printf("sorting %d entries...%n", wordCount.size());

        SortedSet<WordCountEntry> wordCountEntries = new TreeSet<>(WordCountEntryComparator.instance());

        wordCount.entrySet().stream()
                .map(stringAtomicLongEntry ->
                        new WordCountEntry(
                                stringAtomicLongEntry.getKey(),
                                stringAtomicLongEntry.getValue().get()
                        )
                ).forEach(e -> {
                    wordCountEntries.add(e);

                    int numOfSortedEntries = wordCountEntries.size();
                    if (numOfSortedEntries % STEP_SIZE_FOR_LOGGING == 0) {
                        System.out.printf("sorted %d entries...%n", numOfSortedEntries);
                    }
                });

        System.out.println("sorting done!");

        return wordCountEntries;
    }
}
