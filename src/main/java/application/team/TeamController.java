package application.team;

import application.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allarviinamae on 20/05/16.
 * <p>
 * Main controller for Team view.
 */
@RestController
public class TeamController extends BaseController {

    private static final String ATTENDANCE_PRESENT = "present";

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CurrentDayAttendanceRepository currentDayAttendanceRepository;

    @Autowired
    DateRepository dateRepository;

    @RequestMapping(value = "/api/team")
    public ResponseEntity<Object> getTeamData(@RequestParam(value = FACEBOOK_ID) String facebookId, @RequestParam
            (value = TOKEN) String token, @RequestParam(value = "teamId") String teamId) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Team team = teamRepository.findOne(Long.valueOf(teamId));

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/team", method = RequestMethod.POST)
    public ResponseEntity<Object> markAttendance(@RequestParam(value = FACEBOOK_ID) String facebookId, @RequestParam
            (value = TOKEN) String token, @RequestParam(value = "teamId") String teamId, @RequestParam(value =
            "attendance") String attendance, @RequestBody MarkAttendanceData markAttendanceData) {

        Connection<Facebook> connection = getFacebookConnection(token);

        if (connection == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Team team = teamRepository.findOne(Long.valueOf(teamId));

        if (ATTENDANCE_PRESENT.equals(attendance)) {
            List<CurrentDayAttendance> currentMonthAttendanceList = team.getCurrentMonthAttendance();

            if (!currentMonthAttendanceList.isEmpty()) {
                boolean isCurrentDayFound = false;
                for (CurrentDayAttendance currentDayAttendance : currentMonthAttendanceList) {
                    if (currentDayAttendance.get_date_().getDay().equals(markAttendanceData.getDay())) {
                        isCurrentDayFound = true;
                        addAttendingMember(markAttendanceData, currentDayAttendance);
                    }
                }
                if (!isCurrentDayFound) {
                    createNewCurrentDayAttendance(currentMonthAttendanceList,
                            markAttendanceData);
                }
            } else {
                createNewCurrentDayAttendance(currentMonthAttendanceList,
                        markAttendanceData);
            }

            // Update currentMonthAttendanceList
            team.setCurrentMonthAttendance(currentMonthAttendanceList);
        }

        teamRepository.save(team);

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    private void addAttendingMember(@RequestBody MarkAttendanceData markAttendanceData, CurrentDayAttendance
            currentDayAttendance) {
        ArrayList<String> attendees = currentDayAttendance.getAttendees();
        attendees.add(markAttendanceData.getMember());
        currentDayAttendance.setAttendees(attendees);
    }

    private void createNewCurrentDayAttendance(List<CurrentDayAttendance> currentMonthAttendanceList,
                                               MarkAttendanceData markAttendanceData) {

        Date _date_ = new Date(markAttendanceData.getDay(), markAttendanceData.getMonth(), markAttendanceData.getYear
                ());
        dateRepository.save(_date_);

        CurrentDayAttendance currentDayAttendance = new CurrentDayAttendance(0, 1, _date_);
        addAttendingMember(markAttendanceData, currentDayAttendance);

        currentDayAttendanceRepository.save(currentDayAttendance);

        currentMonthAttendanceList.add(currentDayAttendance);
    }
}
