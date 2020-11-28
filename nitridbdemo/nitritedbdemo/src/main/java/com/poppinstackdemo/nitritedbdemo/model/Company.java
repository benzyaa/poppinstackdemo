package com.poppinstackdemo.nitritedbdemo.model;

import java.util.List;

public class Company {
    String id;
    String name;
    List<String> branches;
    int employeeCount;
    double aveageIncome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public double getAveageIncome() {
        return aveageIncome;
    }

    public void setAveageIncome(double aveageIncome) {
        this.aveageIncome = aveageIncome;
    }
}