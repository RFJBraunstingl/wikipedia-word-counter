package output;

import model.WordCountEntry;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Map.of;

public class WordCountSorterTest {

    private void assertSorting(Map<String, Long> input, WordCountEntry... expected) {

        LinkedHashMap<String, AtomicLong> transformedInput = new LinkedHashMap<>();
        input.forEach((key, value) -> transformedInput.put(
                key,
                new AtomicLong(value)
        ));

        Assert.assertArrayEquals(
                expected,
                WordCountSorter.sortedWordCount(transformedInput).toArray()
        );
    }

    @Test
    public void simpleComparison_works() {
        assertSorting(of(
                        "abc", 5L,
                        "def", 2L,
                        "ghi", 7L
                ),
                new WordCountEntry("ghi", 7),
                new WordCountEntry("abc", 5),
                new WordCountEntry("def", 2)
        );
    }
}
