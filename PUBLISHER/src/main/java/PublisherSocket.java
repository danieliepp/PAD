import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class PublisherSocket {
    private Socket socket;
    private PayloadHandler handler = new PayloadHandler();
    private BufferedReader reader;
    private PrintWriter writer;

    PublisherSocket(String hostname, int port)
    {
        try
        {
            socket = new Socket(hostname, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }
        catch (ConnectException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Cannot connect to the broker");
            e.printStackTrace();
        }
    }

    public void send(String payload)
    {
        writer.println(payload);
        writer.flush();
    }

}
