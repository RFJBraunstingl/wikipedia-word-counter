package output;

import model.WordCountEntry;
import model.WordCountEntryComparator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class WordCountSorter {
    static Collection<WordCountEntry> sortedWordCount(Map<String, AtomicLong> wordCount) {

        System.out.printf("sorting %d entries...%n", wordCount.size());

        SortedSet<WordCountEntry> wordCountEntries = new TreeSet<>(WordCountEntryComparator.instance());

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
}
