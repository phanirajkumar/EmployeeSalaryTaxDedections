package com.example.EmployeeSalary.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.EmployeeSalary.model.Employee;
@Service
public class EmployeeService {
    private Map<Integer, Employee> employeeMap;

    public EmployeeService() {
        employeeMap = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getEmployeeId(), employee);
    }

    public Employee getEmployeeById(Integer employeeId) {
        return employeeMap.get(employeeId);
    }
}