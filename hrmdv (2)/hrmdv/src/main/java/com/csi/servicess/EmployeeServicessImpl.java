package com.csi.servicess;

import com.csi.entity.Employee;
import com.csi.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServicessImpl {

    @Autowired

    private EmployeeRepo employeeRepo;

    public Employee signUp(Employee employee) {
        return employeeRepo.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employeeList) {
        return employeeRepo.saveAll(employeeList);
    }

    public boolean signIn(String empMailId, String empPassword) {
        Employee employee = employeeRepo.findByEmpEmailIdAndEmpPassword(empMailId, empPassword);

        boolean flag = false;
        if (employee != null && employee.getEmpEmailId().equals(empMailId) && employee.getEmpPassword().equals(empPassword)) {
            flag = true;
        }
        return flag;
    }

    @Cacheable(value = "empId")
    public Optional<Employee> findById(int empId) {
        return employeeRepo.findById(empId);
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Employee update(Employee employee) {
        return employeeRepo.save(employee);
    }

    public void deleteById(int empId) {
        employeeRepo.deleteById(empId);

    }

    public void deleteAll() {
        employeeRepo.deleteAll();
    }
}
