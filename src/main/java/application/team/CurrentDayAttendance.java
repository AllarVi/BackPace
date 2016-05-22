package application.team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by allarviinamae on 22/05/16.
 *
 * Info for the attendance chart on team view.
 */
@Entity
public class CurrentDayAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String date;

    private Integer maleAttendees;

    private Integer femaleAttendees;

    public CurrentDayAttendance() {
    }

    public CurrentDayAttendance(String date, Integer maleAttendees, Integer femaleAttendees) {
        this.date = date;
        this.maleAttendees = maleAttendees;
        this.femaleAttendees = femaleAttendees;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMaleAttendees() {
        return maleAttendees;
    }

    public void setMaleAttendees(Integer maleAttendees) {
        this.maleAttendees = maleAttendees;
    }

    public Integer getFemaleAttendees() {
        return femaleAttendees;
    }

    public void setFemaleAttendees(Integer femaleAttendees) {
        this.femaleAttendees = femaleAttendees;
    }
}
