package com.benjaminHardy.microserviceemployee.controller;

import com.benjaminHardy.microserviceemployee.model.dto.EmployeeDto;
import com.benjaminHardy.microserviceemployee.model.Employee;
import com.benjaminHardy.microserviceemployee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;


import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @ApiIgnore
    @GetMapping
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("/swagger-ui.html");
    }
    @GetMapping("/employees")
    public List<Employee> employees(){
        return employeeService.employees();
    }
    @Operation(
            summary = "Retrieve an employee by Id",
            description = "Get Employee object by specifying his id",
            tags = { "Employee"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @GetMapping("/employees/{employeeId}")
    public Employee findEmployeeById(@PathVariable String employeeId){
        return employeeService.findByEmployeeId(employeeId);
    }

    @PostMapping("/employees")

    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeDto employeeDto){
      Employee employeeAdded =  employeeService.saveEmployee(employeeDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{employeeId}")
                .buildAndExpand(employeeAdded.getEmployeeId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/employees/id/{employeeId}")
    public boolean deleteEmployeeById(@PathVariable String employeeId){
        return employeeService.deleteEmployeeById(employeeId);
    }

    @DeleteMapping("/employees")
    public boolean deleteEmployee(@RequestBody Employee employee){
        return employeeService.deleteEmployee(employee);
    }
}
