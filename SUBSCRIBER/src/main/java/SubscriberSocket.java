import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class SubscriberSocket {
    private Socket socket;
    private PayloadHandler handler;
    private BufferedReader reader;
    private PrintWriter writer;

    public SubscriberSocket(String ip, int port)
    {
        try
        {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            handler = new PayloadHandler();
        }
        catch (IOException e)
        {
            System.out.println("Can't connect to broker");
            e.printStackTrace();
        }
    }

    public void send(String payload)
    {
        try
        {
            System.out.println("sending");
            writer.println(payload);
            writer.flush();

            String fromServer;
            while ((fromServer = reader.readLine()) != null)
            {
                System.out.println(fromServer);
            }
        }
        catch (IOException e)
        {
            writer.close();
            try
            {
                reader.close();
                socket.close();
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}
