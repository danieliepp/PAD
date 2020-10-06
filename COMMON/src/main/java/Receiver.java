import java.net.Socket;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Receiver {
    private Socket socket;
    private String name;
    private boolean isConnected;

    public Receiver(Socket socket, String name, boolean isConnected) {
        this.socket = socket;
        this.name = name;
        this.isConnected = isConnected;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
