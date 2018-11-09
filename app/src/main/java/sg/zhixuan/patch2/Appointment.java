package sg.zhixuan.patch2;

public class Appointment {

    public String apptParty;
    public String apptName;
    public String apptDate;
    public String apptTime;
    public String apptLocation;
    public Integer alarmCode;
    public String apptPartyID;
    public String apptContactImageURL;

    public Appointment() { }

    public Appointment(String apptParty, String apptName, String apptDate, String apptTime, String apptLocation, Integer alarmCode, String apptPartyID, String apptContactImageURL) {
        this.apptParty = apptParty;
        this.apptName = apptName;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.apptLocation = apptLocation;
        this.alarmCode = alarmCode;
        this.apptPartyID = apptPartyID;
        this.apptContactImageURL = apptContactImageURL;
    }

    public String getApptParty() {
        return apptParty;
    }

    public void setApptParty(String apptParty) {
        this.apptParty = apptParty;
    }

    public String getApptName() {
        return apptName;
    }

    public void setApptName(String apptName) {
        this.apptName = apptName;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getApptTime() {
        return apptTime;
    }

    public void setApptTime(String apptTime) {
        this.apptTime = apptTime;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public Integer getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getApptPartyID() {
        return apptPartyID;
    }

    public void setApptPartyID(String apptPartyID) {
        this.apptPartyID = apptPartyID;
    }

    public String getApptContactImageURL() {
        return apptContactImageURL;
    }

    public void setApptContactImageURL(String apptContactImageURL) {
        this.apptContactImageURL = apptContactImageURL;
    }
}