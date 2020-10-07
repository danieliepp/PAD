/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class SubscriberMessageReaderThread implements Runnable {
    private IReadWrite transport;
    public SubscriberMessageReaderThread(IReadWrite transport){
        this.transport=transport;
    }
    public void run() {
        String messageFromServer;   //Message from server
        while(!(messageFromServer = transport.readAsync()).equals("disconnect"))
            System.out.println(messageFromServer);
        System.out.println("Disconnected from broker");
    }
}
