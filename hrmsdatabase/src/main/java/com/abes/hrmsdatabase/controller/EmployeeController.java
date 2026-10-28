package com.abes.hrmsdatabase.controller;

import com.abes.hrmsdatabase.model.Attendance;
import com.abes.hrmsdatabase.model.Employee;
import com.abes.hrmsdatabase.service.AttendenceService;
import com.abes.hrmsdatabase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/v1/user")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String msg(){
        return "Hrms is online";
    }

    @PostMapping("/addemp")
    public Employee addEmp(@RequestBody Employee employee) {
        return employeeService.registerEmployee(employee);
    }

    @GetMapping("findbyid/{empid}")
    public Employee getEmpById(@PathVariable long empid){
        System.out.println("Employee id:"+empid);
    return employeeService.getEmpById(empid);
    }

    @DeleteMapping("deletebyid/{empid}")
    public String deleteById(@PathVariable long empid){
        return employeeService.deleteById(empid);
    }
    @Autowired
    private AttendenceService attendenceService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(
            @RequestBody Attendance attendance) {

        String result = attendenceService.markAttendence(attendance);

        return ResponseEntity.ok(result);
    }
}
