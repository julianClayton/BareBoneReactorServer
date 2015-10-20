
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by julian on 2015-01-27.
 */
public class Body implements Serializable {
    public HashMap content;


    public Body(){
        content = new HashMap<String, String>();
    }
}