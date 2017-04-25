package application.profile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by allarviinamae on 19/05/16.
 * <p>
 * Represents all of users sports related Goals
 */
@Entity
public class Goals implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="GOAL_ID")
    private long id;

    @ElementCollection
    private Map<String, ArrayList<Goal>> goalMap;

    public Goals() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, ArrayList<Goal>> getGoalMap() {
        return goalMap;
    }

    public void setGoalMap(Map<String, ArrayList<Goal>> goalMap) {
        this.goalMap = goalMap;
    }
}
