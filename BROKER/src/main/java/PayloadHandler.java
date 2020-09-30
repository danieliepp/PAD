import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class PayloadHandler {
    private Socket clientSocket;

public int handlePayload(Socket clientSocket, Payload payload) {

    this.clientSocket = clientSocket;

    if(payload.getId() == 0)
    {
        PayloadStorage.add(payload);
        return 1;
    }else if (payload.getId() == 1) {
        PrintWriter writer = null;
        for (Payload payload1 : PayloadStorage.payloads) {
            if (payload1.getMessage().toLowerCase().contains(payload.getTopic().toLowerCase())) {
                try {
                    writer = new PrintWriter(clientSocket.getOutputStream());
                    writer.println("TOPIC:" + payload1.getTopic() + "\n" + "MESSAGE:" + payload1.getMessage());
                    writer.flush();//clear the stream of any element that may be or maybe not inside the stream
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
