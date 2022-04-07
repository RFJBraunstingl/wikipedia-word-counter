package model;

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
}
