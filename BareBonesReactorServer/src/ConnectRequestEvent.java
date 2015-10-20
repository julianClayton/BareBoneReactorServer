import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectRequestEvent implements EventHandler{
	Reactor reactor;
	ConcurrentHashMap<String, ThreadWithReactor> clients;
	
	public ConnectRequestEvent(Reactor r, ConcurrentHashMap<String, ThreadWithReactor> c){
		reactor = r;
		clients = c;
	}
	
	@Override
	public void handleEvent(Event event) throws JSONException {
		clients.put(event.getId(), (ThreadWithReactor)Thread.currentThread() );
    	System.out.println("CURRENTLY CONNECTED: ");
    	for (Object name : clients.keySet()){
	    	System.out.println(name);
	    }
    	
        Body body = new Body();
        Header header = new Header("CONNECTED_RESPONSE", "");
        Message LoggedIn = new Message(header, body);
        JSONObject jsonLoggedIn = LoggedIn.messageToJson();
        BufferedWriter writer = event.getWriter();
        JsonSender.sendJson(writer, jsonLoggedIn);             
        
        
        //Now to sendThe server then sends a USERS_UPDATED message to all connected users, This message contains a list of all connected users.
        Header header2 = new Header("USERS_UPDATED", "");
        Body body2 = new Body();
        int i = 0;
        //Add current clients to players hashmap within the body class, this will send a list of all the users who are currently connected 
        for (Object name : clients.keySet()){
        	body2.content.put(i, name);
        	i++;
        }
        
        Message userMessage = new Message(header2, body2);
        JSONObject userMessageJson = userMessage.messageToJson();
        
        for (Object name: clients.keySet()){
        	ThreadWithReactor TWR = clients.get(name);
        	JSONEventSource es = (JSONEventSource) TWR.getEventSource();
        	try {
				es.write(userMessageJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        JsonSender.sendJson(writer, userMessageJson);
    }
}

