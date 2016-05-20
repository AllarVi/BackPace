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
    private List<ShortTableRow> shortTableRowMap;

    public ShortTeamView() {
    }


    public List<ShortTableRow> getShortTableRowMap() {
        return shortTableRowMap;
    }

    public void setShortTableRowMap(List<ShortTableRow> shortTableRowMap) {
        this.shortTableRowMap = shortTableRowMap;
    }
}
