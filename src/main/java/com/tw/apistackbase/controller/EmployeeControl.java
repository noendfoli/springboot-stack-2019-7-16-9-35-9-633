package com.tw.apistackbase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by jxzhong on 18/08/2017.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeControl {
    private static  List<Employee> employees = new ArrayList<>();
    static {
        employees.add(new Employee(0,"李栋",20,"male"));
        employees.add(new Employee(1,"owen",21,"male"));
        employees.add(new Employee(2,"liowen",22,"male"));
        employees.add(new Employee(3,"owen li",23,"female"));
        employees.add(new Employee(4,"lisi",24,"female"));
        employees.add(new Employee(5,"sili",25,"male"));
    }
    @GetMapping()
    public ResponseEntity getAll(@RequestParam(value = "page",required =false )String page,
                                 @RequestParam(value = "pageSize",required = false)String pageSize,
                                 @RequestParam(value = "gender",required = false) String gender){
        if(gender!=null){
            return ResponseEntity.ok().body(getAllmaleEmployee(gender));
        }
        List<Employee> pageEmployees = countPage(page, pageSize);
        if(pageEmployees.size()>0){
            return ResponseEntity.ok().body(pageEmployees);
        }
        return ResponseEntity.ok().body(employees);
    }

    private List<Employee> countPage(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize) {
        List<Employee> paginationEmployees = new LinkedList<>();
        if(page != null && pageSize != null){
            int start = (Integer.valueOf(page)-1)*Integer.valueOf(pageSize);
            int end = Integer.valueOf(page)*Integer.valueOf(pageSize);
            for(int i = start;i<end;i++){
                paginationEmployees.add(employees.get(i));
            }
        }
        return paginationEmployees;
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable int id){
        Employee employee = employees.stream().filter(e -> e.getId()==id).collect(Collectors.toList()).get(0);
        return ResponseEntity.ok().body(employee);
    }
    public List<Employee> getAllmaleEmployee(String gender){
        return employees.stream().filter(e -> e.getGender().equals(gender)).collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity addEmployee(@RequestBody Employee employee){
        employees.add(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable int id){
        Employee employe = new Employee();
        for(Employee employee:employees){
            if(employee.getId() == id){
                employee.setName("xxxx");
                employee.setAge(33);
                employee.setGender("female");
                employe = employee;
            }
        }
        return ResponseEntity.ok().body(employe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id){
        int index = 0;
        for(int i=0;i<employees.size();i++){
            if(employees.get(i).getId() == id){
                index = i;
            }
        }
        employees.remove(employees.get(index));
        return ResponseEntity.ok().build();
    }
}
