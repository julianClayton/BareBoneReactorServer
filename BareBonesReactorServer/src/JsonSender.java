import java.io.BufferedWriter;
import java.io.IOException;

import org.json.JSONObject;



/**Could'nt come up with a better name. Contains methods to send JSON, serialize, deserialize JSON
 * @author Julian
 *
 */
public  class JsonSender {
	
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
