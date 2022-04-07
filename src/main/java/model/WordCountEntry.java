package model;

import java.util.Objects;

public class WordCountEntry {

    private final String word;
    private final long count;

    public WordCountEntry(String word, long count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCountEntry that = (WordCountEntry) o;
        return count == that.count && Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }
}
