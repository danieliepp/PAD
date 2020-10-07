import java.io.IOException;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Broker {
    public static void main(String[] args) throws IOException {
        System.out.println("BROKER");
        IReadWrite broker=new BrokerSocket();
        String msg;
        while(true)
        {
            while(!(msg=broker.readAsync()).isEmpty())
                if(msg.equals("invalid"))
                {
                    System.out.println("INVALID MESSAGE");
                }
                else
                {
                    System.out.println("VALID MESSAGE");
                    broker.writeAsync(msg);
                }
        }
    }
}
