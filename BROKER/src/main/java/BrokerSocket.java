import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class BrokerSocket {
    private ServerSocket serverSocket;

    public void startBroker(){
        try {
            //Creating the ServerSocket objetc
            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException e) {
            System.out.println("ERROR: Cannot connect to the PORT");
        }
        while (true) {
            try {
                //Listens for a connection to be made to this socket and accepts it.
                Socket clientSocket = serverSocket.accept();
                //Let's start a thread that will maintain the broker on it!
                BrokerThread brokerThread = new BrokerThread(clientSocket);
                Thread thread = new Thread(brokerThread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
