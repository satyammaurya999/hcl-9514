package com.abes.hrmsdatabase.service;

import com.abes.hrmsdatabase.model.Attendance;
import com.abes.hrmsdatabase.repository.AttendenceRepository;
import com.abes.hrmsdatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendenceService {

    @Autowired
    private AttendenceRepository attendenceRepository;


    @Autowired
    private EmployeeRepository employeeRepository;


    public String markAttendence(Attendance attendence) {

        Long empId = (long) attendence.getEmployee().getId();

        // 1. Check employee exists
        if (!employeeRepository.existsById(empId)) {
            return "Employee does not exist";
        }

        // 2. Check attendance already marked today
        boolean alreadyMarked =
                attendenceRepository.existsByEmployee_IdAndAttendenceDate(
                        empId,
                        LocalDate.now()
                );

        if (alreadyMarked) {
            return "Attendance already marked for today";
        }

        // 3. Set today's date
        attendence.setAttendenceDate(LocalDate.now());

        // 4. Save attendance
        attendenceRepository.save(attendence);

        return "Attendance marked successfully";
    }
}