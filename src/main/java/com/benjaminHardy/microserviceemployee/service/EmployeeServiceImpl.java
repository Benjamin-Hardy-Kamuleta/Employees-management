package com.benjaminHardy.microserviceemployee.service;

import com.benjaminHardy.microserviceemployee.dao.EmployeeDao;
import com.benjaminHardy.microserviceemployee.model.dto.EmployeeDto;
import com.benjaminHardy.microserviceemployee.exception.EmployeeNotFoundException;
import com.benjaminHardy.microserviceemployee.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public List<Employee> employees() {
        logger.info("getting all employees");
        return employeeDao.findAll();
    }
    @Override
    public Employee findByEmployeeId(String employeeId) {
           return employeeDao.findById(employeeId).orElseThrow(
                   ()->new EmployeeNotFoundException("Employee with id "+employeeId+" is not found"));
    }



    @Override
    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee employeeToSave = new Employee();
        employeeToSave.setEmployeeFirstName(employeeDto.employeeFirstName());
        employeeToSave.setEmployeeLastName(employeeDto.employeeLastName());
        employeeToSave.setEmployeeBirthDate(employeeDto.employeeBirthDate());
        employeeToSave.setEmployeeCommitDate(employeeDto.employeeCommitDate());
        return employeeDao.save(employeeToSave);
    }

    @Override
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public boolean deleteEmployeeById(String employeeId) {
      Optional<Employee> employeeToDelete =  employeeDao.findAll().stream()
              .filter(employee -> employee.getEmployeeId().equals(employeeId)).findFirst();
      if (employeeToDelete.isPresent()){
          employeeDao.deleteById(employeeId);
          return true;
      }
        return false;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        Optional<Employee> employeeToDelete =  employeeDao.findAll().stream()
                .filter(empl -> empl.getEmployeeId().equals(employee.getEmployeeId())).findFirst();
        if (employeeToDelete.isPresent()){
            employeeDao.delete(employee);
            return true;
        }
        return false;
    }
}
