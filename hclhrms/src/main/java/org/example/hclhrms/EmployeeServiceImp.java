package org.example.hclhrms;

import org.example.hclhrms.entity.Employee;
import org.example.hclhrms.repository.EmployeeImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp {

    @Autowired
    private EmployeeImp employeeImp;


    // ADD
    public String addEmp(Employee employee) {

        return employeeImp.addemp(employee);
    }


    // GET ALL
    public List<Employee> getEmployee() {

        return employeeImp.getEmp();
    }


    // GET BY ID
    public Employee getEmployeeById(long empid) {

        return employeeImp.getEmpId(empid);
    }


    // DELETE
    public String deleteEmployeeById(long empid) {

        return employeeImp.deleteById(empid);
    }


    // UPDATE
    public String updateEmployeeById(long empid, Employee employee) {

        return employeeImp.updateById(empid, employee);
    }
    // BULK ADD
    public String addBulkEmployees(List<Employee> employees) {

        return employeeImp.addBulkEmp(employees);
    }
}