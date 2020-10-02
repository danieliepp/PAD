import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class BrokerThread implements Runnable {

    private final Socket clientSocket;
    private final PayloadHandler handler;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public BrokerThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        try
        {
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e)
        {
            System.out.println("Can't open stream!");
        }
        handler = new PayloadHandler();
    }

    public void run() {
        try {

            String inputLine;
            int outputLine;

            while ((inputLine = reader.readLine()) != null){
                Gson gson = new Gson();
                Payload payload = gson.fromJson(inputLine, Payload.class);

                outputLine = handler.handlePayload(clientSocket, payload);
                if(outputLine == 0){
                    writer.println("Disconnecting...");
                    writer.flush();
                    writer.close();
                    reader.close();
                    clientSocket.close();
                }
//                else if(outputLine == 1){
//                    writer = new PrintWriter(clientSocket.getOutputStream());
//                    writer.print("!!!!!!!");
//                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
