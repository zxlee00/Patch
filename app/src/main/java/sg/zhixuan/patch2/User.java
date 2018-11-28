package sg.zhixuan.patch2;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    public String uid;
    public String name;
    public int age;
    public String phoneNumber;
    public String profilePic;
    public String hobby;
    public String gender;
    public String lastSignedIn;

    public User() {
    }

    public User(String name, String profilePic, String uid) {
        this.name = name;
        this.profilePic = profilePic;
        this.uid = uid;
    }

    public User(String uid, String name, int age, String phoneNumber, String profilePic, String hobby, String gender, String lastSignedIn) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
        this.hobby = hobby;
        this.gender = gender;
        this.lastSignedIn = lastSignedIn;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastSignedIn() {
        return lastSignedIn;
    }

    public void setLastSignedIn(String lastSignedIn) {
        this.lastSignedIn = lastSignedIn;
    }
}
