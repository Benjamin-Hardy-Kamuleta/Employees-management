package com.benjaminHardy.microserviceemployee.model.dto;

import java.time.LocalDate;

public record EmployeeDto(String employeeFirstName,String employeeLastName,
                          LocalDate employeeBirthDate,LocalDate employeeCommitDate,double employeeSalary) {
}
