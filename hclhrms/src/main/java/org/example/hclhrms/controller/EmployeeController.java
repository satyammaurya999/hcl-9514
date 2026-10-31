package org.example.hclhrms.controller;

import org.example.hclhrms.EmployeeServiceImp;
import org.example.hclhrms.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImp employeeServiceImp;


    // HOME
    @GetMapping("/")
    public ResponseEntity<String> wel() {

        return ResponseEntity.ok("Welcome to HRMS");
    }


    // ADD EMPLOYEE
    @PostMapping("/addemp")
    public ResponseEntity<String> addNewEmployee(
            @RequestBody Employee employee) {

        String result = employeeServiceImp.addEmp(employee);

        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }


    // GET ALL EMPLOYEES
    @GetMapping("/getemp")
    public ResponseEntity<List<Employee>> getEmployee() {

        List<Employee> employees =
                employeeServiceImp.getEmployee();

        return ResponseEntity.ok(employees);
    }


    // GET EMPLOYEE BY ID
    @GetMapping("/getempbyid/{empid}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable long empid) {

        Employee employee =
                employeeServiceImp.getEmployeeById(empid);

        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee with id "
                            + empid
                            + " not found");
        }

        return ResponseEntity.ok(employee);
    }


    // UPDATE EMPLOYEE
    @PutMapping("/update/{empid}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable long empid,

            @RequestBody Employee employee) {

        Employee existingEmployee =
                employeeServiceImp.getEmployeeById(empid);

        if (existingEmployee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee with id "
                            + empid
                            + " not found");
        }

        String result =
                employeeServiceImp.updateEmployeeById(
                        empid,
                        employee
                );

        return ResponseEntity.ok(result);
    }


    // DELETE EMPLOYEE
    @DeleteMapping("/delete/{empid}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable long empid) {

        Employee employee =
                employeeServiceImp.getEmployeeById(empid);

        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee with id "
                            + empid
                            + " not found");
        }

        String result =
                employeeServiceImp.deleteEmployeeById(empid);

        return ResponseEntity.ok(result);
    }
    @PostMapping("/addbulk")
    public ResponseEntity<String> addBulkEmployees(
            @RequestBody List<Employee> employees) {

        String result =
                employeeServiceImp.addBulkEmployees(employees);

        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }
}