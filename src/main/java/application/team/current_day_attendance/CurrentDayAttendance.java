package application.team.current_day_attendance;

import application.team.Date;
import application.team.attendee.Attendee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allarviinamae on 22/05/16.
 * <p>
 * Info for the attendance chart on team view.
 */
@Entity
public class CurrentDayAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer maleAttendees;

    private Integer femaleAttendees;

    @OneToOne
    private Date _date_;

    @ElementCollection
    private List<Attendee> attendees;

    public CurrentDayAttendance() {
    }

    public CurrentDayAttendance(Integer maleAttendees, Integer femaleAttendees, Date _date_) {
        this.attendees = new ArrayList<>();

        this.maleAttendees = maleAttendees;
        this.femaleAttendees = femaleAttendees;
        this._date_ = _date_;
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

    public Date get_date_() {
        return _date_;
    }

    public void set_date_(Date _date_) {
        this._date_ = _date_;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }
}
