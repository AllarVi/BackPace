package application.team;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkAttendanceData {

    public MarkAttendanceData() {
    }

    public MarkAttendanceData(String day, String month, String year, String member) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.member = member;
    }

    @JsonProperty("day")
    private String day;

    @JsonProperty("month")
    private String month;

    @JsonProperty("year")
    private String year;

    @JsonProperty("member")
    private String member;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
