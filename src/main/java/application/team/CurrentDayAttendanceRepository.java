package application.team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentDayAttendanceRepository extends JpaRepository<CurrentDayAttendance, Long> {
}
