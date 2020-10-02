import java.io.*;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class PayloadHandler {

    public int handlePayload(Socket clientSocket, Payload payload) {

        if(payload.getId() == 0) {
        PayloadStorage.add(payload);
        return 1;
    } else if (payload.getId() == 1) {
        for (Payload payload1 : PayloadStorage.payloads) {
            if (payload1.getTopic().toLowerCase().contains(payload.getTopic().toLowerCase())) {
                try {
                    System.out.println("the topic is:  " + payload.getTopic() + " and " + payload1.getTopic());
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                    writer.write("TOPIC:" + payload1.getTopic() + "\n" + "MESSAGE:" + payload1.getMessage());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    } else {
        return -1;
    }
}
}
