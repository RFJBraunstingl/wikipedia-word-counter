package threading;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class InputProcessorThread<T> extends Thread {

    private final BlockingQueue<T> inputQueue;
    private final T poisonPill;
    private final Consumer<T> consumer;

    public InputProcessorThread(
            BlockingQueue<T> inputQueue,
            T poisionPill,
            Consumer<T> consumer) {

        this.inputQueue = inputQueue;
        this.poisonPill = poisionPill;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                T nextInput = inputQueue.take();
                if (poisonPill.equals(nextInput)) {
                    break;
                }

                consumer.accept(nextInput);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
