import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Publisher...");

        PublisherSocket publisherSocket = new PublisherSocket(Constants.HOSTNAME, Constants.PORT);

        int id = 1;
        String topic, message;

        Gson gson = new GsonBuilder().create();

        while(true)
        {
            System.out.println("Enter the topic:");
            topic = scanner.nextLine();
            System.out.println("Enter the message:");
            message = scanner.nextLine();

            Payload payload = new Payload(id, topic, message);
            String payloadInJsonString = gson.toJson(payload);
            publisherSocket.send(payloadInJsonString);
        }
    }
}
