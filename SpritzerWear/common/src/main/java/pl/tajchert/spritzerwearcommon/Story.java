package pl.tajchert.spritzerwearcommon;


import org.json.JSONException;
import org.json.JSONObject;

public class Story {
    public String title;
    public String content;

    public Story() {
    }

    public Story(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
