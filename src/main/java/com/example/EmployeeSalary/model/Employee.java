package com.example.EmployeeSalary.model;

import java.time.LocalDate;
import java.util.List;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;
    private LocalDate doj;
    private double salary;

    public Employee(int employeeId, String firstName, String lastName, String email, List<String> phoneNumbers,
                    LocalDate doj, double salary) {
        super();
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
        this.doj = doj;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double calculateTotalSalary(LocalDate startOfFinancialYear, LocalDate endOfFinancialYear) {
        LocalDate adjustedJoiningDate = doj.isBefore(startOfFinancialYear) ? startOfFinancialYear : doj;
        LocalDate adjustedEndDate = doj.isAfter(endOfFinancialYear) ? endOfFinancialYear : doj.plusMonths(1)
                .withDayOfMonth(1);
        long numberOfMonths = adjustedJoiningDate.until(adjustedEndDate, java.time.temporal.ChronoUnit.MONTHS);
        return salary * numberOfMonths;
    }

    public double calculateLossOfPayPerDay() {
        return salary / 30;
    }
}