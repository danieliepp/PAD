import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class BrokerSocket implements IReadWrite {

    public ServerSocket serverSocket;
    private List<Letter> letterList;
    private List<Receiver> receiverList;

    public BrokerSocket() {
        letterList = new ArrayList<>();
        receiverList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(Constants.PORT);
        }catch (IOException e) {
            System.out.println("Cannot create broker socket");
            e.printStackTrace();
        }
    }

    @Override
    public String readAsync() {
        Socket connectionSocket = null;
        Consumer<Receiver> styleRec = (Receiver p) -> System.out.println("Name: "+p.getName() +", Socket: "+p.getSocket());
        Consumer<Letter> printLetterConsumer= (Letter l)-> System.out.print("Name:"+l.getName()+", Message text: "+l.getMessage());
        try {
            //creating receiver's socket
            connectionSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket finalConnectionSocket = connectionSocket;
        ExecutorService executor = Executors.newSingleThreadExecutor(); //creating a thread with Thread Executor
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                InputStream inputStream = null;
                StringBuffer result = new StringBuffer();
                try {
                    inputStream = finalConnectionSocket.getInputStream();
                    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(inputStream));
                    String partlyTransData;
                    while (!(partlyTransData = receiveRead.readLine()).isEmpty()){
                        result.append(partlyTransData.trim());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                String message = result.toString();
                String answer = "valid";
                System.out.println("Data has been received from buffer");
                System.out.println(message + "  brokerscoket 67");

                DataParserManager xml = new DataParserManager(message);
                if(message.length()>=9 && message.substring(0,8).equals("connect ")) {
                    String name = message.substring(8, message.length());
                    System.out.println("** " + name + " parsed to be connected");
                    receiverList.add(new Receiver(finalConnectionSocket,name, true));
                } else if(message.length()>=12 && message.substring(0,11).equals("disconnect ")) {
                    String name = message.substring(11, message.length());
                    System.out.println("parsed receiver: " + name + " to be disconnected");

                    letterList.add(new Letter(name, "disconnect\n"));
                }else if(xml.isXML()){
                    System.out.println("--Parsed data--");
                    String msg=xml.getMessage();
                    System.out.println("Message from method : "+msg);
                    List<String>rec=xml.getReceivers();
                    System.out.println("Receivers : "+rec);
                    for (String s : rec) letterList.add(new Letter(s, msg + "\n"));
                }else {
                    answer = "invalid";
                    System.out.println("Message is not valid");
                    int port=finalConnectionSocket.getPort();
                    for (Receiver receiver : receiverList)
                        if (receiver.getSocket().getPort() == port) {
                            letterList.add(new Letter(receiver.getName(), "null" + "\n"));
                            break;
                        }
                }
                System.out.println("Receiver List: ");
                receiverList.forEach(styleRec);
                System.out.println("Letters List");
                letterList.forEach(printLetterConsumer);
                return answer;
            }
        };
        Future<String> future = executor.submit(task);
        String message = null;
        while(!future.isDone());
        try{
            message=future.get();
        } catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace(System.err);
        }
        executor.shutdown();
        return message;
    }

    @Override
    public void writeAsync(String message) {

    }
}
