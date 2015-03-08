package pl.tajchert.spritzerwearcommon;


import org.json.JSONException;
import org.json.JSONObject;

public class Story {

    private String title;

    private String content;
    private int positionEndedReading; //split content String by spaces

    public Story() {
    }

    public Story(StoryRealm storyRealm) {
        this.title = storyRealm.getTitle();
        this.content = storyRealm.getContent();
        this.positionEndedReading = storyRealm.getPositionEndedReading();
    }

    public Story(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Story(String title, String content, int positionEndedReading) {
        this.title = title;
        this.content = content;
        this.positionEndedReading = positionEndedReading;
    }

    public StoryRealm toStoryRealm(){
        return new StoryRealm(this.title, this.content, this.positionEndedReading);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPositionEndedReading() {
        return positionEndedReading;
    }

    public void setPositionEndedReading(int positionEndedReading) {
        this.positionEndedReading = positionEndedReading;
    }
}
