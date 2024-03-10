package com.benjaminHardy.microserviceemployee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    private String employeeId = makeRegistrationNumber();
    private String employeeFirstName;
    private String employeeLastName;
    private LocalDate employeeBirthDate;
    private LocalDate employeeCommitDate;
    private double employeeSalary;

    private String makeRegistrationNumber(){
        StringBuilder registrationNumber= new StringBuilder();
        String[] parts = UUID.randomUUID().toString().split("-");
        for (String part : parts){
            registrationNumber.append(part, 0, 2);
        }
        return registrationNumber.toString();
    }

}
