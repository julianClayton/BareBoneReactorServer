
/**
 * Created by julian on 2015-01-22.
 */

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	static ConcurrentHashMap<String, ThreadWithReactor> clients;;

	
    public static void main(String[] args) throws ClassNotFoundException {
    	
        
    	/**HashMap that contains all information on currently connected clients
    	 * Use ThreadWithReactor to send messages back to specific clients 
    	 */
    	clients = new ConcurrentHashMap<String, ThreadWithReactor>();
        
        ServerSocket serverSocket = null;
        final Reactor reactor = new Reactor();
        int port = 6712;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while (true) {
            try {
            	
            	System.out.println("Waiting on port " + port);
            	
            	ConnectRequestEvent connectRequestEvent = new ConnectRequestEvent(reactor, clients);
            	ExampleEvent exampleEvent = new ExampleEvent(reactor);
            	
            	
                reactor.register("EXAMPLE_EVENT", exampleEvent);
                reactor.register("CONNECT_REQUEST", connectRequestEvent);
                
    
                Socket listener = serverSocket.accept();
                JSONEventSource eventSource = new JSONEventSource(listener);
                ThreadWithReactor TWR = new ThreadWithReactor(eventSource, reactor);
                TWR.start();
                System.out.println("Connected!");
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public static void sendJson(BufferedWriter writer, JSONObject jo){
        try {
            writer.write(jo.toString() + "\n");
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}