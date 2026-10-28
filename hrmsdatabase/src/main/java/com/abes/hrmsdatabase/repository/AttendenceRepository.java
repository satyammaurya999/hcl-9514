package com.abes.hrmsdatabase.repository;

import com.abes.hrmsdatabase.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AttendenceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByEmployee_IdAndAttendenceDate(
            Long employeeId,
            LocalDate attendenceDate
    );
}