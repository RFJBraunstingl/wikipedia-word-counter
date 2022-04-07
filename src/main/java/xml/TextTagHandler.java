package xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import threading.TextProcessor;

public class TextTagHandler extends DefaultHandler {

    private static final int STEP_SIZE_FOR_LOGGING = 1_000;
    private static final String TEXT_TAG = "text";
    private static final int TAG_COUNT_LIMIT = 10_000_000;

    private final TextProcessor textProcessor;
    private int textTagCounter = 0;
    private StringBuilder textBuffer;
    private boolean isTextProcessorFinished = false;

    public TextTagHandler() {
        this(new TextProcessor());
    }

    public TextTagHandler(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (TEXT_TAG.equalsIgnoreCase(qName)) {
            textBuffer = new StringBuilder();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (textBuffer != null) {
            textBuffer.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (!TEXT_TAG.equalsIgnoreCase(qName)) {
            return;
        }

        String text = textBuffer.toString();

        ++textTagCounter;
        if (textTagCounter % STEP_SIZE_FOR_LOGGING == 0) {
            System.out.printf("text tags found: %d%n", textTagCounter);
            System.out.printf("Queue size is %s%n", textProcessor.getQueueSize());
        }

        if (textTagCounter < TAG_COUNT_LIMIT) {
            textProcessor.enqueue(text);
        } else if (textTagCounter == TAG_COUNT_LIMIT) {
            textProcessor.finish();
            isTextProcessorFinished = true;
        }
    }

    @Override
    public void endDocument() {
        if (!isTextProcessorFinished) {
            textProcessor.finish();
        }
    }
}
