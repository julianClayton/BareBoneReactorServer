
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by julian on 2015-01-27.
 */
public class Message implements Serializable{
    public Header header;
    public Body body;
    public String retId;

    public Message(Header h, Body b){
        body = b;
        header = h;
        retId= "";
    }

    public void setBody(Body b){
        body = b;
    }

    private JSONObject headerToJson() throws JSONException {
        JSONObject headerJson = new JSONObject();
        headerJson.put("id", header.id);
        headerJson.put("seqNo", header.seqNo);
        headerJson.put("type", header.type);
        return headerJson;
    }
    private JSONObject bodyToJson() throws JSONException {
        JSONObject bodyJson = new JSONObject();
        JSONObject content = new JSONObject(body.content);
        bodyJson.put("content", content);
        return bodyJson;
    }

    public JSONObject messageToJson() throws JSONException {
        JSONObject messageJSon = new JSONObject();

        messageJSon.put("Body", bodyToJson());
        messageJSon.put("Header", headerToJson());
        messageJSon.put("retId", retId);

        return messageJSon;
    }
}
