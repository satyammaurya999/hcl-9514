package org.example.hclhrms.repository;

import org.example.hclhrms.entity.Employee;

import java.util.List;

public interface EmployeeRepo {

    String addemp(Employee employee);

    List<Employee> getEmp();

    Employee getEmpId(long empid);

    String deleteById(long empid);

    String updateById(long empid, Employee employee);
    String addBulkEmp(List<Employee> employees);
}