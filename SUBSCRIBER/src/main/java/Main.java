import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

/**
 * Copyright [] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("com.utm.receiver.Receiver...");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the topic:");
        String topic = scanner.nextLine();

        Payload payload = new Payload(0, topic, null);

        SubscriberSocket socket = new SubscriberSocket(Constants.HOSTNAME, Constants.PORT);

        Gson gson = new GsonBuilder().create();
        String payloadInJsonString = gson.toJson(payload);

        socket.send(payloadInJsonString);
    }
}
