package com.abes.hrmsdatabase.service;

import com.abes.hrmsdatabase.model.Employee;
import com.abes.hrmsdatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee registerEmployee(Employee employee){
        employeeRepository.save(employee);
        return employee;
    }

    public Employee getEmpById(long empid){
        return employeeRepository.findById(empid).orElse(null);
    }

    public String deleteById(long empid){
        if (!employeeRepository.existsById(empid)) {
            return "Employee not found";
        }

        employeeRepository.deleteById(empid);
        return "Employee removed successfully";
    }
}
