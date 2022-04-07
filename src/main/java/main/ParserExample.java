package main;

import org.xml.sax.SAXException;
import xml.ExampleHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ParserExample {

    private static final String WIKIPEDIA_DUMP_PATH = "/Users/rbraunstingl/Downloads/dewiki-20220401.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
        SAXParser saxParser = factory.newSAXParser();

        ExampleHandler handler = new ExampleHandler();

        long beforeTimeInNanos = System.nanoTime();
        saxParser.parse(WIKIPEDIA_DUMP_PATH, handler);
        long afterTimeInNanos = System.nanoTime();

        System.out.println("done! :)");

        long deltaInNanos = afterTimeInNanos - beforeTimeInNanos;
        long ns = deltaInNanos % 1000;
        long deltaInUs = deltaInNanos / 1000;
        long us = deltaInUs % 1000;
        long deltaInMs = deltaInUs / 1000;
        long ms = deltaInMs % 1000;
        long deltaInSec = deltaInMs / 1000;
        long sec = deltaInSec % 60;
        long min = deltaInSec / 60;
        System.out.printf("it took %d minutes, %d seconds, and %d milliseconds%n", min, sec, ms);
    }
}
