package com.abes.hrmsdatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Employee {
    @Id
    private int id;
    private String name;
    private String dept;
    private String progStack;

    public Employee(){
    }

    public Employee(int id, String progStack, String dept, String name) {

        this.id = id;
        this.progStack = progStack;
        this.dept = dept;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getProgStack() {
        return progStack;
    }

    public void setProgStack(String progStack) {
        this.progStack = progStack;
    }


}
