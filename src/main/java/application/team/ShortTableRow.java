package application.team;

import application.user.PaceUser;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by allarviinamae on 15/05/16.
 * <p>
 * Represents a row of short team table.
 */
@Entity
public class ShortTableRow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer rank;

    private String userName;

    private String tier;

    private Integer points;

    private String paceUserFacebookId;

    public ShortTableRow(Integer rank, String userName, String tier, Integer points) {
        this.rank = rank;
        this.userName = userName;
        this.tier = tier;
        this.points = points;
    }

    public ShortTableRow(Integer rank, String userName, String tier, Integer points, String paceUserFacebookId) {
        this.rank = rank;
        this.userName = userName;
        this.tier = tier;
        this.points = points;
        this.paceUserFacebookId = paceUserFacebookId;
    }

    public ShortTableRow() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getPaceUserFacebookId() {
        return paceUserFacebookId;
    }

    public void setPaceUserFacebookId(String paceUserFacebookId) {
        this.paceUserFacebookId = paceUserFacebookId;
    }
}
