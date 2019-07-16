package com.tw.apistackbase.controller;

import java.util.LinkedList;
import java.util.List;

public class Company {
    private int id;
    private List<Employee> employees = new LinkedList<Employee>();

    public int getId() {
        return id;
    }

    public Company() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Company(int id, List<Employee> employees) {
        this.id = id;
        this.employees = employees;
    }
}
