package com.benjaminHardy.microserviceemployee.dao;

import com.benjaminHardy.microserviceemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, String> {
}
