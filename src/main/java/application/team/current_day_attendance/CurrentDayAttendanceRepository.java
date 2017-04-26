package application.team.current_day_attendance;

import application.team.current_day_attendance.CurrentDayAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentDayAttendanceRepository extends JpaRepository<CurrentDayAttendance, Long> {
}
