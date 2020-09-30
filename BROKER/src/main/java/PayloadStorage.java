import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class PayloadStorage {
    public static Queue<Payload> payloads = new ConcurrentLinkedDeque<Payload>();

    public static void add(Payload payload)
    {
        payloads.add(payload);
    }

    public static Payload getNext()
    {
        return payloads.peek();
    }

    public static boolean isEmpty()
    {
        return payloads.isEmpty();
    }

    public static boolean remove(Payload payload)
    {
        return payloads.remove(payload);
    }
}
