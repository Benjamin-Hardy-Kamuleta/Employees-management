package com.benjaminHardy.microserviceemployee.controller;

import com.benjaminHardy.microserviceemployee.model.dto.EmployeeDto;
import com.benjaminHardy.microserviceemployee.model.Employee;
import com.benjaminHardy.microserviceemployee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
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
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @ApiIgnore
    @GetMapping
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("/swagger-ui.html");
    }

    @Operation(
            summary = "Retrieve all employees",
            description = "Get the list of all employees",
            tags = { "All employees"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Employee[].class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/employees")
    public List<Employee> employees(){
        return employeeService.employees();
    }
    @Operation(
            summary = "Retrieve an employee by Id",
            description = "Get Employee object by specifying his id",
            tags = { "Get an employee"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/employees/{employeeId}")
    public Employee findEmployeeById(@PathVariable String employeeId){
        return employeeService.findByEmployeeId(employeeId);
    }
    @Operation(
            summary = "Add an employee to database",
            description = "Add an employee to database by specifying employeeDto object. The Response is an employee object",
            tags = { "Add an employee"})
    @ApiResponse(responseCode = "201", description = "new employee created", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "400", description = "Bad request: employeeDto",content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
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

    @Operation(
            summary = "Update an existing employee",
            description = "Update an existing employee, if the provided employee id is not found then this " +
                    "employee will be added as new employee",
            tags = { "Edit an employee"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "400", description = "Bad request: employee", content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @Operation(
            summary = "Delete an employee by Id",
            description = "Delete an employee object by specifying his id. The response is true if the employee is found " +
                    "and deleted, otherwise the response will be false",
            tags = { "Delete employee by id"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "202", description = "The operation is accepted but the result will be false", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @DeleteMapping("/employees/id/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable String employeeId){
        if (employeeService.deleteEmployeeById(employeeId)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

    @Operation(
            summary = "Delete an employee",
            description = "Delete an employee object by specifying the object. " +
                    "The response is true if the employee is found " +
                    "and deleted, otherwise the response will be false",
            tags = { "Delete employee"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "202", description = "The operation is accepted but the result will be false", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @DeleteMapping("/employees")
    public ResponseEntity<Boolean> deleteEmployee(@RequestBody Employee employee){
        if (employeeService.deleteEmployee(employee)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }
}
