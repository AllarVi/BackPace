package application.team;

import application.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            "attendance") String attendance, @RequestParam(value = "date") String date) {

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
                    if (currentDayAttendance.getDate().equals(date)) {
                        isCurrentDayFound = true;
                        Integer femaleAttendees = currentDayAttendance.getFemaleAttendees();
                        femaleAttendees = femaleAttendees + 1;
                        currentDayAttendance.setFemaleAttendees(femaleAttendees);
                    }
                }
                if (!isCurrentDayFound) {
                    createNewCurrentDayAttendance(date, currentMonthAttendanceList);
                }
            } else {
                createNewCurrentDayAttendance(date, currentMonthAttendanceList);
            }

            team.setCurrentMonthAttendance(currentMonthAttendanceList);
        }

        teamRepository.save(team);

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    private void createNewCurrentDayAttendance(@RequestParam(value = "date") String date, List<CurrentDayAttendance>
            currentMonthAttendanceList) {
        CurrentDayAttendance currentDay = new CurrentDayAttendance(date, 0, 1);
        currentDayAttendanceRepository.save(currentDay);

        currentMonthAttendanceList.add(currentDay);
    }
}
