package sg.zhixuan.patch2;

public class MatchUpUser {

    public String name;
    public String uid;
    public String type;

    public MatchUpUser() {
    }

    public MatchUpUser(String name, String uid, String type) {
        this.name = name;
        this.uid = uid;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
