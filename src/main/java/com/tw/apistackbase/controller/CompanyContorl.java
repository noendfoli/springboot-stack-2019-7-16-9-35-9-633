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
@RequestMapping("/companies")
public class CompanyContorl {
    private static  List<Company> companies = new ArrayList<>();
    static {
        List<Employee>  employees = new LinkedList<>();
        employees.add(new Employee(0,"李栋",20,"male"));
        employees.add(new Employee(1,"owen",21,"male"));
        employees.add(new Employee(2,"liowen",22,"male"));
        employees.add(new Employee(3,"owen li",23,"female"));
        employees.add(new Employee(4,"lisi",24,"female"));
        employees.add(new Employee(5,"sili",25,"male"));
        companies.add(new Company(1,employees));
    }
    @GetMapping()
    public ResponseEntity getAll(@RequestParam(value = "page",required =false )String page,
                                 @RequestParam(value = "pageSize",required = false)String pageSize){

        List<Company> paginationCompanies = countPage(page, pageSize);
        if(paginationCompanies.size()>0){
            return ResponseEntity.ok().body(paginationCompanies);
        }
        return ResponseEntity.ok().body(companies);
    }

    private List<Company> countPage(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "pageSize", required = false) String pageSize) {
        List<Company> paginationCompanies = new LinkedList<>();
        if(page != null && pageSize != null){
            int start = (Integer.valueOf(page)-1)*Integer.valueOf(pageSize);
            int end = Integer.valueOf(page)*Integer.valueOf(pageSize);
            if(end>companies.size()){
                for(int i = start;i<end;i++){
                    paginationCompanies.add(companies.get(i));
                }
            }else{
                for(int i = start;i<end;i++){
                    paginationCompanies.add(companies.get(i));
                }
            }
        }
        return paginationCompanies;
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity getEmployees (@PathVariable int id){
        Company company = companies.stream().filter(c -> c.getId()==id).collect(Collectors.toList()).get(0);
        return ResponseEntity.ok().body(company);
    }

    @PostMapping()
    public ResponseEntity addEmployee(@RequestBody Company company){
        companies.add(company);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable int id){
        Company company = new Company();
        for(Company c:companies){
            if(c.getId() == id){
                List<Employee>  employees = new LinkedList<>();
                employees.add(new Employee(0,"xx",20,"male"));
                employees.add(new Employee(1,"yy",21,"male"));
                employees.add(new Employee(2,"xxyy",22,"male"));
                employees.add(new Employee(3,"xyxy li",23,"female"));
                employees.add(new Employee(4,"xzxz",24,"female"));
                employees.add(new Employee(5,"xcxc",25,"male"));
                c.setEmployees(employees);
                company = c;
            }
        }
        return ResponseEntity.ok().body(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompany(@PathVariable int id){
        int index = 0;
        for(int i=0;i<companies.size();i++){
            if(companies.get(i).getId() == id){
                index = i;
            }
        }
        companies.remove(companies.get(index));
        return ResponseEntity.ok().build();
    }
}
