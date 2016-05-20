package application.user;

import application.profile.Goals;
import application.team.ShortTeamView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class PaceUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String facebookId;

    private String name;

    private String authResponse;

    private String picture;

    private String accessToken;

    @ElementCollection
    private List<ShortTeamView> shortTeamViewList;

    @OneToOne
    private Goals goals;

    public PaceUser() {
    }

    public PaceUser(String name, String facebookId) {
        this.name = name;
        this.facebookId = facebookId;
    }

    @Override
    public String toString() {
        return String.format("PaceUser[id=%d, name='%s', facebookId='%s']", id, name, facebookId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(String authResponse) {
        this.authResponse = authResponse;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<ShortTeamView> getShortTeamViewList() {
        return shortTeamViewList;
    }

    public void setShortTeamViewList(List<ShortTeamView> shortTeamViewList) {
        this.shortTeamViewList = shortTeamViewList;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }
}


