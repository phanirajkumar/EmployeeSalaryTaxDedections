package com.example.EmployeeSalary.EmployeeController;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeSalary.Service.EmployeeService;
import com.example.EmployeeSalary.model.Employee;
import com.example.EmployeeSalary.model.TaxDeduction;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        // Validate the data
    	if (employee.getEmployeeId() == 0) {
    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee ID is required");
    	}
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First Name is required");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last Name is required");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        if (employee.getPhoneNumbers() == null || employee.getPhoneNumbers().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone Numbers are required");
        }
        if (employee.getDoj() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date of Joining is required");
        }
        if (employee.getSalary() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Salary must be greater than zero");
        }

        // Store the employee details
        employeeService.addEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee details stored successfully");
    }

    @GetMapping("/{employeeId}/tax-deduction")
    public ResponseEntity<TaxDeduction> calculateTaxDeduction(@PathVariable Integer employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate startOfFinancialYear = LocalDate.of(currentDate.getYear(), java.time.Month.APRIL, 1);
        LocalDate endOfFinancialYear = LocalDate.of(currentDate.plusYears(1).getYear(), java.time.Month.MARCH, 31);

        double totalSalary = employee.calculateTotalSalary(startOfFinancialYear, endOfFinancialYear);

        double yearlySalary = totalSalary;
        double taxAmount = 0;
        if (yearlySalary > 1000000) {
            taxAmount += (yearlySalary - 1000000) * 0.2;
            yearlySalary = 1000000;
        }
        if (yearlySalary > 500000) {
            taxAmount += (yearlySalary - 500000) * 0.05;
        }
        if (yearlySalary > 250000) {
            taxAmount += (yearlySalary - 250000) * 0.05;
        }

        double cessAmount = 0;
        if (yearlySalary > 2500000) {
            cessAmount = (yearlySalary - 2500000) * 0.02;
        }

        TaxDeduction taxDeduction = new TaxDeduction(
        	    Integer.toString(employee.getEmployeeId()),
        	    employee.getFirstName(),
        	    employee.getLastName(),
        	    yearlySalary,
        	    taxAmount,
        	    cessAmount);
        taxDeduction.setEmployeeCode(Integer.toString(employee.getEmployeeId()));
        taxDeduction.setFirstName(employee.getFirstName());
        taxDeduction.setLastName(employee.getLastName());
        taxDeduction.setYearlySalary(yearlySalary);
        taxDeduction.setTaxAmount(taxAmount);
        taxDeduction.setCessAmount(cessAmount);

        return ResponseEntity.status(HttpStatus.OK).body(taxDeduction);
    }
}