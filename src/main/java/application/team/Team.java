package application.team;

import application.team.current_day_attendance.CurrentDayAttendance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allarviinamae on 15/05/16.
 *
 * Represents sports Team/Group.
 */
@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String teamName;

    @ElementCollection
    private List<ShortTableRow> fullScoresTableList;

    @ElementCollection
    private List<CurrentDayAttendance> currentMonthAttendance;

    public Team() {
        currentMonthAttendance = new ArrayList<>();
        fullScoresTableList = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ShortTableRow> getFullScoresTableList() {
        return fullScoresTableList;
    }

    public void setFullScoresTableList(List<ShortTableRow> fullScoresTableList) {
        this.fullScoresTableList = fullScoresTableList;
    }

    public List<CurrentDayAttendance> getCurrentMonthAttendance() {
        return currentMonthAttendance;
    }

    public void setCurrentMonthAttendance(List<CurrentDayAttendance> currentMonthAttendance) {
        this.currentMonthAttendance = currentMonthAttendance;
    }
}
