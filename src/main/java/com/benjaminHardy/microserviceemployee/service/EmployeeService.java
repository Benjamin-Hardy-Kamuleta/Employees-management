package com.benjaminHardy.microserviceemployee.service;

import com.benjaminHardy.microserviceemployee.model.dto.EmployeeDto;
import com.benjaminHardy.microserviceemployee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> employees();
    Employee findByEmployeeId(String employeeId);
    Employee saveEmployee(EmployeeDto employeeDto);
    //Employee saveEmployee(Employee employee);
    Employee updateEmployee(Employee employee);

    boolean deleteEmployeeById(String employeeId);
    boolean deleteEmployee(Employee employee);

}
