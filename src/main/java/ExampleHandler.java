import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ExampleHandler extends DefaultHandler {

    private static final int STEP_SIZE_FOR_LOGGING = 1_000;
    private static final String TEXT_TAG = "text";

    private final TextProcessor textProcessor = new TextProcessor();

    private int textTagCounter = 0;
    private StringBuilder textBuffer;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (TEXT_TAG.equalsIgnoreCase(qName)) {
            textBuffer = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String text = textBuffer.toString();
        textProcessor.enqueue(text);

        ++textTagCounter;
        if (textTagCounter % STEP_SIZE_FOR_LOGGING == 0) {
            System.out.printf("text tags found: %d%n", textTagCounter);
            System.out.printf("Queue size is %s%n", textProcessor.getQueueSize());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (textBuffer == null) {
            textBuffer = new StringBuilder();
        } else {
            textBuffer.append(ch, start, length);
        }
    }

    @Override
    public void endDocument() {
        textProcessor.shutdown();
    }
}
