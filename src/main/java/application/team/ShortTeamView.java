package application.team;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by allarviinamae on 15/05/16.
 *
 * Represents data to be displayed.
 */
@Entity
public class ShortTeamView extends Team {

    @ElementCollection
    private List<ShortTableRow> shortTableRowList;

    public ShortTeamView() {
    }


    public List<ShortTableRow> getShortTableRowList() {
        return super.getFullScoresTableList();
    }

    public void setShortTableRowList(List<ShortTableRow> shortTableRowList) {
        this.shortTableRowList = shortTableRowList;
    }
}
