package pl.tajchert.spritzerwearcommon;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoryRealm extends RealmObject {

    @PrimaryKey
    private String title;

    private String content;
    private int positionEndedReading; //split content String by spaces

    public StoryRealm() {
    }

    public StoryRealm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public StoryRealm(String title, String content, int positionEndedReading) {
        this.title = title;
        this.content = content;
        this.positionEndedReading = positionEndedReading;
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
