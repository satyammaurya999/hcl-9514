package org.example.hclhrms.repository;

import org.example.hclhrms.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeImp implements EmployeeRepo {

    private final List<Employee> emplist = new ArrayList<>();


    @Override
    public String addemp(Employee employee) {

        emplist.add(employee);

        return "Employee added successfully";
    }


    @Override
    public List<Employee> getEmp() {

        return emplist;
    }


    @Override
    public Employee getEmpId(long empid) {

        // stream() nahi use kiya
        for (Employee employee : emplist) {

            if (employee.getEmp_id() == empid) {
                return employee;
            }
        }

        return null;
    }


    @Override
    public String deleteById(long empid) {

        for (int i = 0; i < emplist.size(); i++) {

            if (emplist.get(i).getEmp_id() == empid) {

                emplist.remove(i);

                return "Employee deleted successfully";
            }
        }

        return "Employee not found";
    }


    @Override
    public String updateById(long empid, Employee employee) {

        for (int i = 0; i < emplist.size(); i++) {

            if (emplist.get(i).getEmp_id() == empid) {


                employee.setEmp_id(empid);

                emplist.set(i, employee);

                return "Employee updated successfully";
            }
        }

        return "Employee not found";
    }
    @Override
    public String addBulkEmp(List<Employee> employees) {

        emplist.addAll(employees);

        return employees.size() + " employees added successfully";
    }
}