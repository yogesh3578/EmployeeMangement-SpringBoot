package com.csi.controller;

import com.csi.entity.Employee;
import com.csi.exception.RecordNotFoundException;
import com.csi.servicess.EmployeeServicessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class EmployeeController {

    @Autowired

    private EmployeeServicessImpl employeeServicess;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


    @PostMapping("/signUp")

    public ResponseEntity<Employee> signUp(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeServicess.signUp(employee), HttpStatus.CREATED);

    }

    @PostMapping("/saveAll")

    public ResponseEntity<List<Employee>> saveAll(@Valid @RequestBody List<Employee> employeeList) {
        return new ResponseEntity<>(employeeServicess.saveAll(employeeList), HttpStatus.CREATED);
    }

    @GetMapping("/signIn/{empEmailId}/{empPassword}")

    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeServicess.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findById/{empId}")

    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServicess.findById(empId));
    }

    @GetMapping("/findbyFirstName/{empFirstName}")

    public ResponseEntity<List<Employee>> findbyFirstName(@PathVariable String empFirstName) {
        return ResponseEntity.ok(employeeServicess.findAll().stream().filter(employee -> employee.getEmpFirstName().equals(empFirstName)).collect(Collectors.toList()));
    }

    @GetMapping("/findByContactNumber/{empContactNumber}")

    public ResponseEntity<Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeServicess.findAll().stream().filter(employee -> employee.getEmpContactNumber() == empContactNumber).toList().get(0));
    }

    @GetMapping("findByUid/{empUid}")

    public ResponseEntity<Employee> findByUid(@PathVariable long empUid) {
        return ResponseEntity.ok(employeeServicess.findAll().stream().filter(emp -> emp.getEmpUid() == empUid).toList().get(0));

    }

    @GetMapping("/findByDob/{empDob}")

    public ResponseEntity<List<Employee>> findByDob(@PathVariable String empDob) {
        return ResponseEntity.ok(employeeServicess.findAll().stream().filter(employee -> simpleDateFormat.format(employee.getEmpDob()).equals(empDob)).toList());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeServicess.findAll());
    }

    @GetMapping("/sortById")

    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeServicess.findAll().stream().sorted(Comparator.comparing(Employee::getEmpId)).toList());
    }

    @GetMapping("/sortByName")

    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeServicess.findAll().stream().sorted(Comparator.comparing(Employee::getEmpFirstName)).toList());

    }

    @GetMapping("/sortBySalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(employeeServicess.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/sortByDob")

    public ResponseEntity<List<Employee>> sortByDob() {
        return ResponseEntity.ok(employeeServicess.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDob)).toList());
    }

    @GetMapping("/filterBySalary/{empSalary}")

    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeServicess.findAll().stream().filter(employee -> employee.getEmpSalary() >= empSalary).toList());
    }

    @GetMapping("/checkLoanEligibility/{empId}")
    public ResponseEntity<String> checkLoanEligibility(@PathVariable int empId) {
        Employee employee = employeeServicess.findById(empId).orElseThrow(() -> new RecordNotFoundException("Id Does Not Exist.."));

        String msg = "";

        if (employee.getEmpSalary() >= 50000.00) {
            msg = "Eligible For Loan";
        } else {
            msg = "Not Eligible for Loan";
        }
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @Valid @RequestBody Employee employee) {
        Employee employee1 = employeeServicess.findById(empId).orElseThrow(() -> new RecordNotFoundException("ID Does Not Exist"));

        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpDob(employee.getEmpDob());
        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpFirstName(employee.getEmpFirstName());
        employee1.setEmpLastName(employee.getEmpLastName());
        employee1.setEmpUid(employee.getEmpUid());
        employee1.setEmpPanCard(employee.getEmpPanCard());

        return new ResponseEntity<>(employeeServicess.update(employee1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteById/{empId}")
    public ResponseEntity<String>deleteById(@PathVariable int empId){
        employeeServicess.deleteById(empId);
        return ResponseEntity.ok("Id Deleted Successfully..");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        employeeServicess.deleteAll();
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @GetMapping("fetchSecondHighesSalary")

    public ResponseEntity<Employee> fetchSecondHighesSalary() {
        return ResponseEntity.ok((Employee) employeeServicess.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList().get(1));
    }

}
