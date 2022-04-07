import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class TextProcessor {

    private static final int NUMBER_OF_PROCESSORS = 12;
    private static final int QUEUE_CAPACITY = 1_000;
    private static final String POISON_PILL = String.valueOf(ThreadLocalRandom.current().nextLong());
    private static final Duration PROCESSOR_THREAD_TIMEOUT = Duration.ofMillis(10_000);

    private final BlockingQueue<String> textQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final List<InputProcessorThread<String>> processorThreads = new ArrayList<>();
    private final Map<String, AtomicLong> wordCount = new ConcurrentHashMap<>();

    public TextProcessor() {
        createProcessorThreads();
    }

    private void createProcessorThreads() {
        for (int i = 0; i < NUMBER_OF_PROCESSORS; i++) {
            InputProcessorThread<String> processorThread = new InputProcessorThread<>(textQueue, POISON_PILL, s -> {
                String[] words = s.split("\\s");
                for (String word : words) {
                    count(WordCleaner.clean(word));
                }
            });

            processorThreads.add(processorThread);
            processorThread.start();
        }
    }

    private void count(String word) {
        if (!wordCount.containsKey(word)) {
            wordCount.put(word, new AtomicLong(1L));
        } else {
            AtomicLong counter = wordCount.get(word);
            counter.incrementAndGet();
        }
    }

    public void enqueue(String text) {
        try {
            textQueue.put(text);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQueueSize() {
        return textQueue.size();
    }

    public void finish() {
        try {
            for (int i = 0; i < NUMBER_OF_PROCESSORS; i++) {
                textQueue.put(POISON_PILL);
            }

            for (InputProcessorThread<String> processorThread : processorThreads) {
                processorThread.join(PROCESSOR_THREAD_TIMEOUT.toMillis());
            }

            ResultReporter.report(wordCount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
