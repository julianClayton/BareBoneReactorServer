import org.json.JSONException;


public class ExampleEvent implements EventHandler {
	Reactor reactor;
	
	public ExampleEvent(Reactor r){
		reactor = r; 
	}
	@Override
	public void handleEvent(Event event) throws JSONException {
		System.out.println("Example Event Dispatched!");
		
	}
	

}
