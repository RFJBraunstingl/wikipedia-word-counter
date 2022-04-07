package model;

import java.util.Comparator;

public class WordCountEntryComparator implements Comparator<WordCountEntry> {

    private static final int COMES_BEFORE = -1;
    private static final int COMES_AFTER = 1;

    public static WordCountEntryComparator instance() {
        return new WordCountEntryComparator();
    }

    private WordCountEntryComparator() {}

    @Override
    public int compare(WordCountEntry o1, WordCountEntry o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            }
            return COMES_BEFORE;
        }

        if (o2 == null) {
            return COMES_AFTER;
        }

        long count1 = o1.getCount();
        long count2 = o2.getCount();

        if (count1 < count2) {
            return COMES_AFTER;
        } else if (count1 > count2) {
            return COMES_BEFORE;
        } else { // count is identical
            return o1.getWord().compareTo(o2.getWord());
        }
    }
}
