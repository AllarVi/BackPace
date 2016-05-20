package application.team;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by allarviinamae on 15/05/16.
 *
 * Represents sports Team/Group.
 */
@Entity
public abstract class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String teamName;

    @ElementCollection
    private List<ShortTableRow> fullScoresTableList;

    protected Team() {
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
}
