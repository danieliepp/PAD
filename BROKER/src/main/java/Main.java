/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Broker...");

        BrokerSocket brokerSocket = new BrokerSocket();
        brokerSocket.startBroker();
    }
}
