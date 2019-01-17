package sg.zhixuan.patch2;

public class Admin {
    public String uid;
    public String name;
    public String email;
    public String gender;

    public Admin() {
    }

    public Admin(String uid, String name, String email, String gender) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
